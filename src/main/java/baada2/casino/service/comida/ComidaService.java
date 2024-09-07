package baada2.casino.service.comida;

import baada2.casino.entity.comida.ComidaDTO;
import baada2.casino.entity.comida.ComidaEntity;
import baada2.casino.entity.comida.TablaDTO;
import baada2.casino.entity.socio.SocioEntity;
import baada2.casino.repository.comida.ComidaRepository;
import baada2.casino.repository.socio.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComidaService {
    private static final double COSTO_ESTANCIA = 18300;
    private static final double COSTO_DESAYUNO_EXTRA = 7000;
    private static final double COSTO_ALMUERZO_EXTRA = 10000;
    private static final double COSTO_CENA_EXTRA = 7000;

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private SocioRepository socioRepository;

    public ComidaDTO crearComida(ComidaDTO comidaDTO) {
        ComidaEntity comidaEntity = new ComidaEntity();
        comidaEntity.setClaseComida(comidaDTO.getClaseComida());
        comidaEntity.setCantidad(comidaDTO.getCantidad());
        comidaEntity.setValorComida(comidaDTO.getValorComida());
        comidaEntity.setFecha(comidaDTO.getFecha());
        comidaEntity.setPago(comidaDTO.isPago());

        // Buscar el socio por documento
        SocioEntity socio = socioRepository.findByDocumento(comidaDTO.getSocioId())
                .orElseThrow(() -> new RuntimeException("Socio no encontrado con documento: " + comidaDTO.getSocioId()));

        // Asignar el socio a la entidad de comida
        comidaEntity.setSocio(socio);

        ComidaEntity savedComida = comidaRepository.save(comidaEntity);

        return mapToDTO(savedComida);
    }

    public List<ComidaDTO> obtenerComidas() {
        return comidaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ComidaDTO mapToDTO(ComidaEntity comidaEntity) {
        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setId(comidaEntity.getId());
        comidaDTO.setCantidad(comidaEntity.getCantidad());
        comidaDTO.setClaseComida(comidaEntity.getClaseComida());
        comidaDTO.setValorComida(comidaEntity.getValorComida());
        comidaDTO.setFecha(comidaEntity.getFecha());
        comidaDTO.setPago(comidaEntity.isPago());
        comidaDTO.setSocioId(comidaEntity.getSocio().getDocumento());
        return comidaDTO;
    }

    public TablaDTO obtenerComidasPorSocio(String documento) {
        SocioEntity socio = socioRepository.findByDocumento(documento)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado con documento: " + documento));

        // Filtrar las comidas que no están pagadas ("pagado" == "N")
        List<ComidaEntity> comidas = comidaRepository.findBySocio(socio).stream()
                .filter(comida -> (!comida.isPago())) // Filtrar solo las no pagadas
                .collect(Collectors.toList());

        Map<LocalDate, List<Integer>> comidasPorFecha = new TreeMap<>(Collections.reverseOrder()); // Orden descendente
        double totalEstancias = 0;
        double totalExtra = 0;

        // Iterar sobre las comidas no pagadas y agregarlas a la tabla
        for (ComidaEntity comida : comidas) {
            List<Integer> cantidades = comidasPorFecha.getOrDefault(comida.getFecha(), Arrays.asList(0, 0, 0)); // desayuno, almuerzo, cena

            switch (comida.getClaseComida()) {
                case "1":
                    cantidades.set(0, cantidades.get(0) + comida.getCantidad()); // Desayuno
                    break;
                case "2":
                    cantidades.set(1, cantidades.get(1) + comida.getCantidad()); // Almuerzo
                    break;
                case "3":
                    cantidades.set(2, cantidades.get(2) + comida.getCantidad()); // Cena
                    break;
            }

            comidasPorFecha.put(comida.getFecha(), cantidades);
        }

        // Calcular estancias y extras solo para las comidas no pagadas
        for (Map.Entry<LocalDate, List<Integer>> entry : comidasPorFecha.entrySet()) {
            List<Integer> cantidades = entry.getValue();
            int desayunos = cantidades.get(0);
            int almuerzos = cantidades.get(1);
            int cenas = cantidades.get(2);

            // Verificar si hay al menos un desayuno, un almuerzo y una cena
            if (desayunos > 0 && almuerzos > 0 && cenas > 0) {
                // Estancia
                totalEstancias += 18300;

                // Calcular extras
                if (desayunos > 1) {
                    totalExtra += (desayunos - 1) * 7000;
                }
                if (almuerzos > 1) {
                    totalExtra += (almuerzos - 1) * 10000;
                }
                if (cenas > 1) {
                    totalExtra += (cenas - 1) * 7000;
                }
            } else {
                // No hay estancia completa, pero se deben calcular los extras
                totalExtra += desayunos * 7000;
                totalExtra += almuerzos * 10000;
                totalExtra += cenas * 7000;
            }
        }

        // Cálculos adicionales (fondo casino, fomento, fondo habitacional)
        double fondoCasino = 50000; // Ejemplo de cálculo del fondo casino (5% de estancias)
        double fomento = 30000;     // Ejemplo de cálculo del fomento (3% de estancias)
        double fondoHabitacional = 90000; // Ejemplo de cálculo del fondo habitacional (2% de estancias)

        // Calcular total a pagar
        double totalPagar = totalEstancias + totalExtra + fondoCasino + fomento + fondoHabitacional;

        // Crear el DTO con los valores calculados
        TablaDTO tablaDTO = new TablaDTO();
        tablaDTO.setMes("Septiembre"); // Por ejemplo, puedes ajustar el mes según sea necesario
        tablaDTO.setFecha(comidasPorFecha);
        tablaDTO.setTotalEstancias(totalEstancias);
        tablaDTO.setTotalExtra(totalExtra);
        tablaDTO.setFondoCasino(fondoCasino);
        tablaDTO.setFomento(fomento);
        tablaDTO.setFondoHabitacional(fondoHabitacional);
        tablaDTO.setTotalPagar(String.format("$%,.2f", totalPagar)); // Formato en string para el total a pagar

        return tablaDTO;
    }






}
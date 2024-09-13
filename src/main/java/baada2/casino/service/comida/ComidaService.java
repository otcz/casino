package baada2.casino.service.comida;

import baada2.casino.entity.comida.ComidaDTO;
import baada2.casino.entity.comida.ComidaEntity;
import baada2.casino.entity.comida.TablaDTO;
import baada2.casino.entity.configuraciones.CostoFondosEstanciaEntity;
import baada2.casino.entity.socio.SocioEntity;
import baada2.casino.repository.comida.ComidaRepository;
import baada2.casino.repository.configuraciones.CostoFondosEstanciaRepository;
import baada2.casino.repository.socio.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComidaService {
    private static final double COSTO_ESTANCIA = 0;
    private static final double COSTO_DESAYUNO_EXTRA = 0;
    private static final double COSTO_ALMUERZO_EXTRA = 0;
    private static final double COSTO_CENA_EXTRA = 0;
    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private CostoFondosEstanciaRepository costoFondosEstanciaRepository;

    CostoFondosEstanciaEntity costoFondosEstanciaEntity = null;

    public ComidaDTO crearComida(ComidaDTO comidaDTO) {
        ComidaEntity comidaEntity = new ComidaEntity();
        comidaEntity.setClaseComida(comidaDTO.getClaseComida());
        comidaEntity.setCantidad(comidaDTO.getCantidad());
        comidaEntity.setValorComida(comidaDTO.getValorComida());
        comidaEntity.setFecha(comidaDTO.getFecha());
        comidaEntity.setPago(comidaDTO.isPago());

        // Busca por el documento primero
        SocioEntity socio = socioRepository.findByDocumento(comidaDTO.getSocioId()).orElse(null);

        // Si no encuentra por documento, intenta con idcard
        if (socio == null) {
            socio = socioRepository.findByIdcard(comidaDTO.getSocioId()).orElse(null);
        }

        // Si después de ambas búsquedas sigue siendo null, lanza una excepción
        else if (socio == null) {
            throw new EntityNotFoundException("Socio no encontrado con documento o idcard: " + comidaDTO.getSocioId());
        }

        // Buscar el socio por id
        CostoFondosEstanciaEntity costoFondosEstanciaEntity = costoFondosEstanciaRepository.findById((comidaDTO.getCostoFondosEstanciaId())).orElseThrow(() -> new RuntimeException("Socio no encontrado con documento: " + comidaDTO.getCostoFondosEstanciaId()));

        // Asignar el socio a la entidad de comida
        comidaEntity.setSocio(socio);
        comidaEntity.setCostoFondosEstancia(costoFondosEstanciaEntity);
        ComidaEntity savedComida = comidaRepository.save(comidaEntity);

        return mapToDTO(savedComida);
    }

    public List<ComidaDTO> obtenerComidas() {
        return comidaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ComidaDTO mapToDTO(ComidaEntity comidaEntity) {
        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setId(comidaEntity.getId());
        comidaDTO.setCantidad(comidaEntity.getCantidad());
        comidaDTO.setClaseComida(comidaEntity.getClaseComida());
        comidaDTO.setValorComida(comidaEntity.getValorComida());
        comidaDTO.setFecha(comidaEntity.getFecha());
        comidaDTO.setPago(comidaEntity.isPago());
        comidaDTO.setSocioId((comidaEntity.getSocio().getDocumento()));
        return comidaDTO;
    }

    public TablaDTO obtenerComidasPorSocio(String documento) {
        SocioEntity socio = socioRepository.findByDocumento(documento).orElseThrow(() -> new RuntimeException("Socio no encontrado con documento: " + documento));


        // Obtenemos todas las comidas del socio (incluidas las pagadas)
        List<ComidaEntity> comidas = comidaRepository.findBySocio(socio);
        if (!comidas.isEmpty()) {
            Map<LocalDate, List<Integer>> comidasPorFecha = new TreeMap<>(Collections.reverseOrder()); // Orden descendente
            double totalEstancias = 0;
            double totalExtra = 0;
            double totalEstanciasPagadas = 0;
            double totalExtraPagadas = 0;

            // Iterar sobre todas las comidas y agregarlas a la tabla
            for (ComidaEntity comida : comidas) {
                // Buscar el socio por documento
                costoFondosEstanciaEntity = costoFondosEstanciaRepository.findById((comida.getCostoFondosEstancia().getId())).orElseThrow(() -> new RuntimeException("Socio no encontrado con documento: " + comida.getCostoFondosEstancia().getId()));

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

            // Calcular estancias y extras para comidas pagadas y no pagadas
            for (Map.Entry<LocalDate, List<Integer>> entry : comidasPorFecha.entrySet()) {
                List<Integer> cantidades = entry.getValue();
                int desayunos = cantidades.get(0);
                int almuerzos = cantidades.get(1);
                int cenas = cantidades.get(2);

                // Verificar si hay al menos un desayuno, un almuerzo y una cena
                boolean allMealsPaid = comidas.stream().filter(comida -> comida.getFecha().equals(entry.getKey())).allMatch(ComidaEntity::isPago);

                if (desayunos > 0 && almuerzos > 0 && cenas > 0) {
                    // Verificar si la estancia completa está pagada
                    if (allMealsPaid) {
                        // Estancia pagada
                        totalEstanciasPagadas += costoFondosEstanciaEntity.getEstancia();
                        // Calcular extras pagados
                        if (desayunos > 1) {
                            totalExtraPagadas += (desayunos - 1) * costoFondosEstanciaEntity.getDesayuno();
                        }
                        if (almuerzos > 1) {
                            totalExtraPagadas += (almuerzos - 1) * costoFondosEstanciaEntity.getAlmuerzo();
                        }
                        if (cenas > 1) {
                            totalExtraPagadas += (cenas - 1) * costoFondosEstanciaEntity.getCena();
                        }
                    } else {
                        // Estancia no pagada
                        totalEstancias += costoFondosEstanciaEntity.getEstancia();

                        // Calcular extras no pagados
                        if (desayunos > 1) {
                            totalExtra += (desayunos - 1) * costoFondosEstanciaEntity.getDesayuno();
                        }
                        if (almuerzos > 1) {
                            totalExtra += (almuerzos - 1) * costoFondosEstanciaEntity.getAlmuerzo();
                        }
                        if (cenas > 1) {
                            totalExtra += (cenas - 1) * costoFondosEstanciaEntity.getCena();
                        }
                    }
                } else {
                    // Calcular extras para comidas individuales (no hay estancia completa)
                    boolean hasUnpaidMeal = comidas.stream().filter(comida -> comida.getFecha().equals(entry.getKey())).anyMatch(comida -> !comida.isPago());

                    if (hasUnpaidMeal) {
                        // Extras no pagados
                        totalExtra += desayunos * costoFondosEstanciaEntity.getDesayuno();
                        totalExtra += almuerzos * costoFondosEstanciaEntity.getAlmuerzo();
                        totalExtra += cenas * costoFondosEstanciaEntity.getCena();
                    } else {
                        // Extras pagados
                        totalExtraPagadas += desayunos * costoFondosEstanciaEntity.getDesayuno();
                        totalExtraPagadas += almuerzos * costoFondosEstanciaEntity.getAlmuerzo();
                        totalExtraPagadas += cenas * costoFondosEstanciaEntity.getCena();
                    }
                }
            }

            // Cálculos adicionales (fondo casino, fomento, fondo habitacional)
            double fondoCasino = socio.isFondoCasino() ? costoFondosEstanciaEntity.getFondoCasino() : 0; // Ejemplo de cálculo del fondo casino (5% de estancias)
            double fomento = socio.isFomento() ? costoFondosEstanciaEntity.getFomento() : 0;   // Ejemplo de cálculo del fomento (3% de estancias)
            double fondoHabitacional = socio.isFondoHabitacional() ? costoFondosEstanciaEntity.getFondoHabitacional() : 0; // Ejemplo de cálculo del fondo habitacional (2% de estancias)

            // Calcular total a pagar
            double totalPagar = totalEstancias + totalExtra + fondoCasino + fomento + fondoHabitacional;

            System.out.println(totalEstancias + "totalEstancias");
            // Crear el DTO con los valores calculados
            TablaDTO tablaDTO = new TablaDTO();
            tablaDTO.setMes(getMesFromFecha(costoFondosEstanciaEntity.getFecha())); // Por ejemplo, puedes ajustar el mes según sea necesario
            tablaDTO.setFecha(comidasPorFecha);
            tablaDTO.setTotalEstancias(totalEstancias);
            tablaDTO.setTotalExtra(totalExtra);
            tablaDTO.setTotalEstanciaPagada(totalEstanciasPagadas);
            tablaDTO.setTotalExtraPagada(totalExtraPagadas);
            tablaDTO.setFondoCasino(fondoCasino);
            tablaDTO.setFomento(fomento);
            tablaDTO.setFondoHabitacional(fondoHabitacional);
            tablaDTO.setTotalPagar(String.format("$%,.2f", totalPagar)); // Formato en string para el total a pagar

            return tablaDTO;
        } else {
            TablaDTO tablaDTO = new TablaDTO();
            if (socio.isFomento()) {
                tablaDTO.setFomento(socio.isFomento() ? costoFondosEstanciaEntity.getFomento() : 0);
            } else if (socio.isFondoCasino()) {
                tablaDTO.setFondoCasino(socio.isFondoCasino() ? costoFondosEstanciaEntity.getFondoCasino() : 0);
            } else if (socio.isFondoHabitacional()) {
                tablaDTO.setFondoHabitacional(socio.isFondoHabitacional() ? costoFondosEstanciaEntity.getFondoHabitacional() : 0);
            }
            return tablaDTO;

        }
    }


    public static String getMesFromFecha(String fecha) {
        // Verificar si la cadena tiene el formato esperado
        if (fecha == null || !fecha.contains("-")) {
            throw new IllegalArgumentException("La fecha debe estar en formato 'yyyy-MM'");
        }

        // Dividir la cadena en dos partes usando el guion como delimitador
        String[] partes = fecha.split("-");

        // Verificar que hay al menos dos partes
        if (partes.length < 2) {
            throw new IllegalArgumentException("La fecha debe contener un guion separador.");
        }

        // Retornar la segunda parte (mes)
        return partes[1];
    }


}
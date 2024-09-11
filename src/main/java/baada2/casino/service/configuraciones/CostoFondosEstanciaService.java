package baada2.casino.service.configuraciones;

import baada2.casino.entity.configuraciones.CostoFondosEstanciaEntity;
import baada2.casino.entity.configuraciones.CostoFondosEstanciasDTO;
import baada2.casino.repository.configuraciones.CostoFondosEstanciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Service
public class CostoFondosEstanciaService {

    @Autowired
    private CostoFondosEstanciaRepository costoRepository;

    // Método para verificar si ya existe un registro con el mismo mes y año
    public CostoFondosEstanciaEntity obtenerPorMesAnio(String mesAnio) {
        return costoRepository.findByFecha(mesAnio);
    }

    public CostoFondosEstanciaEntity crearOActualizarCosto(CostoFondosEstanciasDTO dto) {
        CostoFondosEstanciaEntity costoExistente = obtenerPorMesAnio(dto.getMesAnio());
        if (costoExistente != null) {
            // Actualizamos el registro existente
            costoExistente.setFondoCasino(dto.getFondoCasino());
            costoExistente.setFomento(dto.getFomento());
            costoExistente.setFondoHabitacional(dto.getFondoHabitacional());
            costoExistente.setDesayuno(dto.getDesayuno());
            costoExistente.setAlmuerzo(dto.getAlmuerzo());
            costoExistente.setCena(dto.getCena());
            costoExistente.setEstancia(dto.getEstancia());
            costoExistente.setEspecial(dto.getEspecial());

            return costoRepository.save(costoExistente);
        } else {
            // Creamos un nuevo registro
            CostoFondosEstanciaEntity nuevoCosto = new CostoFondosEstanciaEntity();
            nuevoCosto.setFondoCasino(dto.getFondoCasino());
            nuevoCosto.setFomento(dto.getFomento());
            nuevoCosto.setFondoHabitacional(dto.getFondoHabitacional());
            nuevoCosto.setDesayuno(dto.getDesayuno());
            nuevoCosto.setAlmuerzo(dto.getAlmuerzo());
            nuevoCosto.setCena(dto.getCena());
            nuevoCosto.setEstancia(dto.getEstancia());
            nuevoCosto.setEspecial(dto.getEspecial());
            nuevoCosto.setFecha(dto.getMesAnio());

            return costoRepository.save(nuevoCosto);
        }
    }

    public CostoFondosEstanciaEntity obtenerCostoPorFecha(String fecha) {
        // Asumiendo que hay una columna `fecha` que almacena solo el mes y el año
        return costoRepository.findByFecha(fecha);
    }


}

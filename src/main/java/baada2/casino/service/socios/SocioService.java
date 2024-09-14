package baada2.casino.service.socios;

import baada2.casino.entity.comida.ComidaEntity;
import baada2.casino.entity.configuraciones.CostoFondosEstanciaEntity;
import baada2.casino.entity.socio.*;
import baada2.casino.repository.comida.ComidaRepository;
import baada2.casino.repository.configuraciones.CostoFondosEstanciaRepository;
import baada2.casino.repository.socio.SocioRepository;
import baada2.casino.service.comida.ComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private CostoFondosEstanciaRepository costoFondosEstanciaRepository;

    @Autowired
    private ComidaService comidaService;

    public SocioRegistroDTO crearSocio(SocioRegistroDTO socioDTO) {
        SocioEntity socioEntity = new SocioEntity();
        socioEntity.setNombre(socioDTO.getNombre());
        socioEntity.setDocumento(socioDTO.getDocumento());
        socioEntity.setPassword(socioDTO.getPassword());

        socioEntity.setGrado(socioDTO.getGrado());
        socioEntity.setEstado("EN PROCESO");

        socioEntity.setFondoCasino(false);
        socioEntity.setFondoHabitacional(false);
        socioEntity.setFomento(false);

        SocioEntity savedSocio = socioRepository.save(socioEntity);

        return mapToRegistroDTO(savedSocio);
    }

    // Mapeo de Entity a DTO
    private SocioRegistroDTO mapToRegistroDTO(SocioEntity socioEntity) {
        SocioRegistroDTO socioDTO = new SocioRegistroDTO();
        socioDTO.setNombre(socioEntity.getNombre());
        socioDTO.setDocumento(socioEntity.getDocumento());
        socioDTO.setPassword(socioEntity.getPassword());
        return socioDTO;
    }

    public List<SocioDTO> obtenerSocios() {
        return socioRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SocioDTO autenticarSocio(String documento, String password) {
        Optional<SocioEntity> optionalSocio = socioRepository.findByDocumento(documento);
        if (optionalSocio.isPresent()) {
            SocioEntity socioEntity = optionalSocio.get();
            // Verificar la contraseña proporcionada con la almacenada
            if (password.equals(socioEntity.getPassword())) {
                return mapToDTO(socioEntity);
            }
        }
        return null; // Autenticación fallida
    }


    public RegistrarServicioDTO obtenerSocioPorDocumento(RegistrarServicioDTO registrarServicioDTO) {
        // Busca por el documento primero
        SocioEntity socioEntity = socioRepository.findByDocumento(registrarServicioDTO.getDocumento()).orElse(null);

        // Si no encuentra por documento, intenta con idcard
        if (socioEntity == null) {
            socioEntity = socioRepository.findByIdcard(registrarServicioDTO.getDocumento()).orElse(null);
        }

        // Si después de ambas búsquedas sigue siendo null, lanza una excepción
        if (socioEntity == null) {
            throw new EntityNotFoundException("Socio no encontrado con documento o idcard: " + registrarServicioDTO.getDocumento());
        }


        // Si se encontró, convierte la entidad a DTO
        return convertirRegistrarServicioDTO(socioEntity);
    }

    public SocioDTO obtenerSocioPorDocumento(String documento) {
        // Busca por el documento primero
        SocioEntity socioEntity = socioRepository.findByDocumento(documento).orElse(null);

        // Si no encuentra por documento, intenta con idcard
        if (socioEntity == null) {
            socioEntity = socioRepository.findByIdcard(documento).orElse(null);
        }

        // Si después de ambas búsquedas sigue siendo null, lanza una excepción
        if (socioEntity == null) {
            throw new EntityNotFoundException("Socio no encontrado con documento o idcard: " + documento);
        }


        // Si se encontró, convierte la entidad a DTO
        return mapToDTO(socioEntity);
    }

    public SocioFomentoDTO getSocioFomentosNombreCard(String documento) {
        Optional<SocioEntity> socioEntityOptional = socioRepository.findByDocumento(documento);
        SocioFomentoDTO socioFomentoDTO = new SocioFomentoDTO();
        if (socioEntityOptional.isPresent()) {
            SocioEntity socioEntity = socioEntityOptional.get();
            socioFomentoDTO.setFomento(socioEntity.isFomento());
            socioFomentoDTO.setFondoCasino(socioEntity.isFondoCasino());
            socioFomentoDTO.setFondoHabitacional(socioEntity.isFondoHabitacional());
            socioFomentoDTO.setDocumento(socioEntity.getDocumento());
            socioFomentoDTO.setIdCard(socioEntity.getIdcard());
            socioFomentoDTO.setNombre(socioEntity.getNombre());
            return socioFomentoDTO;
        } else {
            return null; // O podrías lanzar una excepción
        }
    }

    // Método de mapeo de SocioEntity a SocioDTO

    private RegistrarServicioDTO convertirRegistrarServicioDTO(SocioEntity socioEntity) {
        RegistrarServicioDTO registrarServicioDTO = new RegistrarServicioDTO();
        registrarServicioDTO.setGrado(socioEntity.getGrado());
        registrarServicioDTO.setDocumento((socioEntity.getDocumento()));
        registrarServicioDTO.setIdCard((socioEntity.getIdcard()));
        registrarServicioDTO.setNombre(socioEntity.getNombre());
        registrarServicioDTO.setEstado(socioEntity.getEstado());
        return registrarServicioDTO;
    }

    private SocioDTO mapToDTO(SocioEntity socioEntity) {
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setGrado(socioEntity.getGrado());
        socioDTO.setNombre(socioEntity.getNombre());
        socioDTO.setEstado(socioEntity.getEstado());
        socioDTO.setPassword(socioEntity.getPassword());
        socioDTO.setDocumento(socioEntity.getDocumento());

        socioDTO.setFondoCasino(socioEntity.isFondoCasino());
        socioDTO.setFondoHabitacional(socioEntity.isFondoHabitacional());
        socioDTO.setFomento(socioEntity.isFomento());
        return socioDTO;
    }

    public SocioEntity actualizarSocio(SocioFomentoDTO socioDTO) {
        Optional<SocioEntity> optionalSocio = socioRepository.findByDocumento(socioDTO.getDocumento());

        if (optionalSocio.isPresent()) {
            SocioEntity socioEntity = optionalSocio.get();
            // Actualizar los valores de los campos booleanos
            socioEntity.setFondoCasino(socioDTO.isFondoCasino());
            socioEntity.setFomento(socioDTO.isFomento());
            socioEntity.setFondoHabitacional(socioDTO.isFondoHabitacional());
            socioEntity.setIdcard(socioDTO.getIdCard());

            // Guardar la entidad actualizada en la base de datos
            return socioRepository.save(socioEntity);
        } else {
            throw new EntityNotFoundException("Socio no encontrado");
        }
    }

}

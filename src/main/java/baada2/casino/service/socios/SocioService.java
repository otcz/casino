package baada2.casino.service.socios;

import baada2.casino.entity.socio.SocioDTO;
import baada2.casino.entity.socio.SocioEntity;
import baada2.casino.repository.comida.ComidaRepository;
import baada2.casino.repository.socio.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private ComidaRepository comidaRepository;

    public SocioDTO crearSocio(SocioDTO socioDTO) {
        SocioEntity socioEntity = new SocioEntity();
        socioEntity.setGrado(socioDTO.getGrado());
        socioEntity.setNombre(socioDTO.getNombre());
        socioEntity.setEstado(socioDTO.getEstado());
        socioEntity.setPassword(socioDTO.getPassword());
        socioEntity.setDocumento(socioDTO.getDocumento());

        SocioEntity savedSocio = socioRepository.save(socioEntity);

        return mapToDTO(savedSocio);
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


    public SocioDTO obtenerSocioPorDocumento(String documento) {
        Optional<SocioEntity> socioEntityOptional = socioRepository.findByDocumento(documento);

        if (socioEntityOptional.isPresent()) {
            SocioEntity socioEntity = socioEntityOptional.get();
            // Aquí debes convertir SocioEntity a SocioDTO. Supongamos que tienes un método de mapeo:
            return convertirEntidadADTO(socioEntity);
        } else {
            return null; // O podrías lanzar una excepción
        }
    }

    // Método de mapeo de SocioEntity a SocioDTO
    private SocioDTO convertirEntidadADTO(SocioEntity socioEntity) {
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(socioEntity.getId());
        socioDTO.setGrado(socioEntity.getGrado());
        socioDTO.setNombre(socioEntity.getNombre());
        socioDTO.setEstado(socioEntity.getEstado());
        socioDTO.setPassword(socioEntity.getPassword());
        socioDTO.setDocumento(socioEntity.getDocumento());
        return socioDTO;
    }
    private SocioDTO mapToDTO(SocioEntity socioEntity) {
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(socioEntity.getId());
        socioDTO.setGrado(socioEntity.getGrado());
        socioDTO.setNombre(socioEntity.getNombre());
        socioDTO.setEstado(socioEntity.getEstado());
        socioDTO.setPassword(socioEntity.getPassword());
        socioDTO.setDocumento(socioEntity.getDocumento());
        return socioDTO;
    }
}

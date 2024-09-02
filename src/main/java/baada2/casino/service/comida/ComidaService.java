package baada2.casino.service.comida;

import baada2.casino.entity.comida.ComidaDTO;
import baada2.casino.entity.comida.ComidaEntity;
import baada2.casino.entity.socio.SocioEntity;
import baada2.casino.repository.comida.ComidaRepository;
import baada2.casino.repository.socio.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComidaService {

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private SocioRepository socioRepository;

    public ComidaDTO crearComida(ComidaDTO comidaDTO) {
        ComidaEntity comidaEntity = new ComidaEntity();
        comidaEntity.setClaseComida(comidaDTO.getClaseComida());
        comidaEntity.setValorComida(comidaDTO.getValorComida());
        comidaEntity.setFecha(comidaDTO.getFecha());
        comidaEntity.setPago(comidaDTO.isPago());

        SocioEntity socio = socioRepository.findById(comidaDTO.getSocioId())
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));

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
        comidaDTO.setClaseComida(comidaEntity.getClaseComida());
        comidaDTO.setValorComida(comidaEntity.getValorComida());
        comidaDTO.setFecha(comidaEntity.getFecha());
        comidaDTO.setPago(comidaEntity.isPago());
        comidaDTO.setSocioId(comidaEntity.getSocio().getId());
        return comidaDTO;
    }
}
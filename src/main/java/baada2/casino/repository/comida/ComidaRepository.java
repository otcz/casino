package baada2.casino.repository.comida;

import baada2.casino.entity.comida.ComidaEntity;
import baada2.casino.entity.socio.SocioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ComidaRepository extends JpaRepository<ComidaEntity, Long> {
    List<ComidaEntity> findBySocio(SocioEntity socio);

    List<ComidaEntity> findBySocioAndFechaBetween(SocioEntity socio, LocalDate fechaInicio, LocalDate fechaFin);

}

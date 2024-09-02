package baada2.casino.repository.socio;

import baada2.casino.entity.socio.SocioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SocioRepository extends JpaRepository<SocioEntity, Long> {
    Optional<SocioEntity> findByDocumento(String documento);
}

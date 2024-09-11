package baada2.casino.repository.configuraciones;


import baada2.casino.entity.configuraciones.CostoFondosEstanciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostoFondosEstanciaRepository extends JpaRepository<CostoFondosEstanciaEntity, Long> {
    CostoFondosEstanciaEntity findByFecha(String fecha);

    Optional<CostoFondosEstanciaEntity> findById(Long id);

}


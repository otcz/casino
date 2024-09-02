package baada2.casino.entity.comida;

import baada2.casino.entity.socio.SocioEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "comida")
public class ComidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "clase_comida", nullable = false)
    private String claseComida;

    @Column(name = "valor_comida", nullable = false)
    private double valorComida;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "pago", nullable = false)
    private boolean pago;

    @ManyToOne
    @JoinColumn(name = "socio_id")
    private SocioEntity socio;


}

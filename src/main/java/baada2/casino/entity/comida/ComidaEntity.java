package baada2.casino.entity.comida;

import baada2.casino.entity.configuraciones.CostoFondosEstanciaEntity;
import baada2.casino.entity.socio.SocioEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate fecha;

    @Column(name = "pago", nullable = false)
    private boolean pago;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;


    // Relación muchas comidas a un socio (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "socio_id", nullable = false)
    private SocioEntity socio;

    // Relación muchas comidas a un costo (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "costo_fondos_estancia_id", nullable = false)
    private CostoFondosEstanciaEntity costoFondosEstancia;

}

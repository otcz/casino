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

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "desayuno", nullable = false)
    private int desayuno;

    @Column(name = "almuerzo", nullable = false)
    private int almuerzo;

    @Column(name = "cena", nullable = false)
    private int cena;

    @Column(name = "valor_estancia", nullable = false)
    private double valorEstancia;

    @Column(name = "valor_extra", nullable = false)
    private double valorEstra;

    @Column(name = "pago", nullable = false)
    private boolean pago;

    // Relación muchas comidas a un socio (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "socio_id", nullable = false)
    private SocioEntity socio;

}

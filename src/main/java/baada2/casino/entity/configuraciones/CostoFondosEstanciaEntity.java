package baada2.casino.entity.configuraciones;

import baada2.casino.entity.comida.ComidaEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(
        name = "costofondosestancia",
        uniqueConstraints = {@UniqueConstraint(columnNames = "fecha")}
)
public class CostoFondosEstanciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "fondo_casino", nullable = false)
    private double fondoCasino=0;

    @Column(name = "fomento", nullable = false)
    private double fomento=0;

    @Column(name = "fondo_habitacional", nullable = false)
    private double fondoHabitacional=0;

    @Column(name = "desayuno", nullable = false)
    private double desayuno=0;

    @Column(name = "almuerzo", nullable = false)
    private double almuerzo=0;

    @Column(name = "cena", nullable = false)
    private double cena=0;

    @Column(name = "estancia", nullable = false)
    private double estancia=0;

    @Column(name = "especial", nullable = false)
    private double especial=0;

    @Column(name = "fecha", nullable = false, unique = true)  // También puedes usar unique = true aquí
    private String fecha;

}

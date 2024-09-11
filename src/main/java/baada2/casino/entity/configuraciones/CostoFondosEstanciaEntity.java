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
    private double fondoCasino;

    @Column(name = "fomento", nullable = false)
    private double fomento;

    @Column(name = "fondo_habitacional", nullable = false)
    private double fondoHabitacional;

    @Column(name = "desayuno", nullable = false)
    private double desayuno;

    @Column(name = "almuerzo", nullable = false)
    private double almuerzo;

    @Column(name = "cena", nullable = false)
    private double cena;

    @Column(name = "estancia", nullable = false)
    private double estancia;

    @Column(name = "especial", nullable = false)
    private double especial;

    @Column(name = "fecha", nullable = false, unique = true)  // También puedes usar unique = true aquí
    private String fecha;

}

package baada2.casino.entity.configuraciones;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CostoFondosEstanciasDTO {
    private Double almuerzo;
    private Double cena;
    private Double desayuno;
    private Double especial;
    private Double estancia;

    private Double fondoCasino;
    private Double fondoHabitacional;
    private Double fomento;

    private String mesAnio;
}

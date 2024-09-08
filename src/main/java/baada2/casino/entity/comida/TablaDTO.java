package baada2.casino.entity.comida;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class TablaDTO {
    private String mes;
    private Map<LocalDate, List<Integer>> fecha = new HashMap<>();
    private double totalEstancias;
    private double fondoCasino = 0;
    private double fomento = 0;
    private double fondoHabitacional = 0;
    private String totalPagar = "0";
    private double totalExtra = 0;
    private double totalExtraPagada = 0;
    private double totalEstanciaPagada = 0;


}

package baada2.casino.entity.socio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SocioFomentoDTO {
    private String documento;
    private String idCard;
    private String nombre;

    private boolean fondoCasino=false;
    private boolean fomento=false;
    private boolean fondoHabitacional=false;

    private double fondoCasinoValor=0;
    private double fomentoValor=0;
    private double fondoHabitacionalValor=0;

}

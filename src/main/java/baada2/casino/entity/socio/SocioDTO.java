package baada2.casino.entity.socio;

import baada2.casino.entity.comida.ComidaDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@ToString
public class SocioDTO {
    private String grado;
    private String nombre;
    private String documento;
    private String estado;
    private String id;
    private String idCard;
    private String password;

    private boolean fondoCasino=false;
    private boolean fomento=false;
    private boolean fondoHabitacional=false;

}

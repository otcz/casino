package baada2.casino.entity.socio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SocioRegistroDTO {
    private String grado;
    private String nombre;
    private String documento;
    private String password;
}

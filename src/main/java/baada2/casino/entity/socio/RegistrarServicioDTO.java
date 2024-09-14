package baada2.casino.entity.socio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class RegistrarServicioDTO {
    private String grado= "";
    private String nombre = "";
    private String estado= "";
    private String idCard= "";
    private String documento;
    private double valorComida;
    private int cantidad;
    private String tipoServicio;
    private LocalDate fechaServicio;
    private boolean rfid;
    private boolean pagado;
    private Long costoFondosEstanciaId;





}

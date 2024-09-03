package baada2.casino.entity.comida;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ComidaDTO {
    private Long id;
    private int cantidad;
    private String claseComida;
    private double valorComida;
    private LocalDate fecha;
    private boolean pago;
    private String socioId;
}

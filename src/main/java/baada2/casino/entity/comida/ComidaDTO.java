package baada2.casino.entity.comida;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ComidaDTO {
    private Long id;
    private String claseComida;
    private double valorComida;
    private LocalDateTime fecha;
    private boolean pago;
    private Long socioId;
}

package baada2.casino.entity.socio;

import baada2.casino.entity.comida.ComidaEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "socio")
public class SocioEntity {

    @Id
    @Column(name = "documento", nullable = false)
    private String documento;

    @Column(name = "idcard", nullable = true)
    private String string;

    @Column(name = "grado", nullable = false)
    private String grado;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "estado", nullable = true)
    private String estado;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fondocasino", nullable = true)
    private boolean fondoCasino;

    @Column(name = "fomento", nullable = true)
    private boolean fomento;

    @Column(name = "fondohabitacional", nullable = true)
    private boolean fondoHabitacional;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComidaEntity> comidaEntityList;
}

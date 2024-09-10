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

    @Column(name = "grado", nullable = false)
    private String grado;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "estado", nullable = true)
    private String estado;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fondocasino", nullable = true)
    private double fondoCasino;

    @Column(name = "fomento", nullable = true)
    private double fomento;

    @Column(name = "fondohabitacional", nullable = true)
    private double fondoHabitacional;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComidaEntity> comidaEntityList;
}

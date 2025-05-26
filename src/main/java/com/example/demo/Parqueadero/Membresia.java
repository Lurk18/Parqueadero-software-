package com.example.demo.Parqueadero;

import com.example.demo.User.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "membresias")
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, unique = true)
    private User usuario;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "vigente", nullable = false)
    private Boolean vigente;

    public Membresia (User usuario) {
        this.usuario = usuario;
        this.fechaInicio = LocalDate.now();
        this.fechaFin = this.fechaInicio.plusMonths(1);
        this.vigente = true;
    }
}

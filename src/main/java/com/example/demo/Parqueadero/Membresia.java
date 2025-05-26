package com.example.demo.Parqueadero;

import com.example.demo.User.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "membresias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private User usuario;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private boolean vigente;

    // Método para renovar (extiende la fecha fin)
    public void renovar() {
        this.fechaInicio = LocalDate.now();
        this.fechaFin = fechaInicio.plusMonths(1);
        this.vigente = true;
    }

    // Método para cancelar
    public void cancelar() {
        this.vigente = false;
    }
}

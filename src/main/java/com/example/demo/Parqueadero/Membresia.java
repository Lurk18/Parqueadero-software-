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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private User usuario;

    @Column(nullable = false, length = 100)
    private String nombre = "Membresía Única";  // Fijo

    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias = 30;  // Fijo 30 días

    @Column(name = "duracion_d", nullable = false)
    private Integer duracionD = 30; // Campo para mapear la columna 'duracion_d' y evitar error de null

    @Column(nullable = false)
    private Double precio = 50.0;  // Precio mensual fijo

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private boolean vigente;

    // Método para renovar (extiende la fecha fin)
    public void renovar() {
        this.fechaInicio = LocalDate.now();
        this.fechaFin = fechaInicio.plusDays(duracionDias);
        this.vigente = true;
        this.duracionD = duracionDias; // Asegurar que no quede null en la columna
    }

    // Método para cancelar
    public void cancelar() {
        this.vigente = false;
    }
}

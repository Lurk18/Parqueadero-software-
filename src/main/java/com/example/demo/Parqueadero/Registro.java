package com.example.demo.Parqueadero;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import com.example.demo.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registros")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parqueadero_id", nullable = false)
    private Parqueadero parqueadero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_ad", nullable = false)
    private User usuarioAdministrador;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    // Método para asignar la fecha de entrada por defecto al crear un registro
    @PrePersist
    protected void onCreate() {
        if (fechaEntrada == null) {
            fechaEntrada = LocalDateTime.now();
        }
    }

    // Método para finalizar el registro y marcar la hora de salida
    public void finalizarRegistro() {
        this.fechaSalida = LocalDateTime.now();
    }

    // Método para saber si está activo
    public boolean estaActivo() {
        return fechaSalida == null;
    }
}

package com.example.demo.Parqueadero;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.example.demo.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "La placa es obligatoria")
    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String tipo;

    @Size(max = 50)
    @Column(length = 50)
    private String color;


    @JoinColumn(name = "usuario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User usuario;

    @PrePersist
    @PreUpdate
    private void prepararDatos() {
        if (placa != null) {
            placa = placa.toUpperCase().trim();
        }
        if (tipo != null) {
            tipo = tipo.trim();
        }
        if (color != null) {
            color = color.trim();
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", placa, tipo, color);
    }
}

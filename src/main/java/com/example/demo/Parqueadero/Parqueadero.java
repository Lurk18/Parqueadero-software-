package com.example.demo.Parqueadero;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parqueaderos")
public class Parqueadero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 10)
    private String codigo;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;
}

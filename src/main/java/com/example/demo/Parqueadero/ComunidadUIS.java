package com.example.demo.Parqueadero;

import com.example.demo.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "comunidad_uis")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ComunidadUIS {

    @Id
    @Column(name = "codigo_uis", nullable = false)
    private Integer codigoUIS;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, unique = true)
    private User usuario;

    public ComunidadUIS (User usuario) {
        this.usuario = usuario;
    }
}

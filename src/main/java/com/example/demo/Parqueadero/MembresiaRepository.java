package com.example.demo.Parqueadero;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MembresiaRepository extends JpaRepository<Membresia, Integer> {
    Optional<Membresia> findByUsuario_Id(Integer userId);
}

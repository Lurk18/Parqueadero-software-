package com.example.demo.Parqueadero;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ComunidadUISRepository extends JpaRepository<ComunidadUIS, Integer> {
    Optional<ComunidadUIS> findByUsuario_Id(Integer userId);
}
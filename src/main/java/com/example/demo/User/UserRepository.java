package com.example.demo.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByCorreo(String correo);
    Optional<User> findByUsuario(String usuario);
    boolean existsByCorreo(String correo);
    boolean existsByUsuario(String usuario);
    Optional<User> findByCorreoAndContraseña(String correo, String contraseña);
}
package com.example.demo.auth;

import com.example.demo.User.Role;
import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        // Buscar usuario por correo
        var user = userRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new InvalidCredentialsException("Credenciales inválidas"));

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getContrasena(), user.getContraseña())) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }

        // Generar token
        var token = jwtService.generateToken(user);

        return AuthResponse.success(token, "Login exitoso");
    }

    public AuthResponse register(RegisterRequest request) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByCorreo(request.getCorreo())) {
            throw new UserAlreadyExistsException("El correo ya está registrado");
        }

        if (userRepository.existsByUsuario(request.getUsuario())) {
            throw new UserAlreadyExistsException("El nombre de usuario ya está en uso");
        }

        // Crear nuevo usuario
        var user = User.builder()
                .nombre(request.getNombre())
                .usuario(request.getUsuario())
                .correo(request.getCorreo())
                .contraseña(passwordEncoder.encode(request.getContraseña()))
                .rol(Role.usuario)
                .build();

        userRepository.save(user);

        // Generar token
        var token = jwtService.generateToken(user);

        return AuthResponse.success(token, "Usuario registrado exitosamente");
    }
}
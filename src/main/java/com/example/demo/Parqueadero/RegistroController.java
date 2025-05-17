package com.example.demo.Parqueadero;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/registro")
@RequiredArgsConstructor
@PreAuthorize("hasRole('administrador')")
public class RegistroController {

    private final RegistroRepository registroRepository;
    private final UserRepository userRepository;
    private final RegistroService registroService;

    // Registrar entrada
    @PostMapping("/entrada")
    public ResponseEntity<Registro> registrarEntrada(@RequestBody Registro registro) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User usuarioAdmin = userRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        registro.setUsuarioAdministrador(usuarioAdmin);

        Registro nuevoRegistro = registroService.registrarEntrada(registro);
        return ResponseEntity.ok(nuevoRegistro);
    }

    // Registrar salida
    @PostMapping("/salida/{id}")
    public ResponseEntity<Map<String, Object>> registrarSalida(@PathVariable Integer id) {
        BigDecimal valor = registroService.registrarSalida(id);

        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));

        Duration duracion = Duration.between(registro.getFechaEntrada(), registro.getFechaSalida());
        long minutos = duracion.toMinutes();
        long horas = minutos / 60;
        long minutosRestantes = minutos % 60;

        Map<String, Object> response = Map.of(
                "mensaje", "Vehículo salió correctamente",
                "valorAPagar", valor,
                "fechaEntrada", registro.getFechaEntrada(),
                "fechaSalida", registro.getFechaSalida(),
                "duracion", horas + " horas y " + minutosRestantes + " minutos"
        );

        return ResponseEntity.ok(response);
    }
}

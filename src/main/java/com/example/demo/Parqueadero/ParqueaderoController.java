package com.example.demo.Parqueadero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parqueaderos")
public class ParqueaderoController {

    @Autowired
    private ParqueaderoService parqueaderoService;

    // Todos los usuarios pueden ver los disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Parqueadero>> obtenerDisponibles() {
        return ResponseEntity.ok(parqueaderoService.obtenerDisponibles());
    }

    // Solo admins deberían ver todos y editar
    @GetMapping
    @PreAuthorize("hasRole('administrador')")
    public ResponseEntity<List<Parqueadero>> obtenerTodos() {
        return ResponseEntity.ok(parqueaderoService.obtenerTodos());
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('administrador')")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, Boolean> request) {

        parqueaderoService.actualizarDisponibilidad(id, request.get("disponible"));
        return ResponseEntity.ok().build();
    }
}
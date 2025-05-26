package com.example.demo.Parqueadero;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/membresias")
@RequiredArgsConstructor
@PreAuthorize("hasRole('administrador')")

public class MembresiaController {

    @Autowired
    private MembresiaService membresiaService;

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<?> obtenerMembresia(@PathVariable Integer userId) {
        return membresiaService.obtenerMembresiaDeUsuario(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/usuario/{userId}")
    public ResponseEntity<?> crearMembresia(@PathVariable Integer userId) {
        try {
            Membresia membresia = membresiaService.crearMembresiaParaUsuario(userId);
            return ResponseEntity.ok(membresia);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/usuario/{userId}/existe")
    public ResponseEntity<Boolean> verificarSiTieneMembresia(@PathVariable Integer userId) {
        return ResponseEntity.ok(membresiaService.usuarioTieneMembresia(userId));
    }

    @GetMapping("/usuario/{userId}/vigente")
    public ResponseEntity<Boolean> esMembresiaVigente(@PathVariable Integer userId) {
        return ResponseEntity.ok(membresiaService.esMembresiaVigente(userId));
    }
}

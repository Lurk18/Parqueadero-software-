package com.example.demo.Parqueadero;

import com.example.demo.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/membresia")
public class MembresiaController {
    private final MembresiaService membresiaService;

    public MembresiaController(MembresiaService membresiaService) {
        this.membresiaService = membresiaService;
    }

    @PostMapping("/comprar")
    public ResponseEntity<Membresia> comprarMembresia(@RequestBody User usuario) {
        return ResponseEntity.ok(membresiaService.crearORenovarMembresia(usuario));
    }

    // Corregido: Recibe usuarioId como path variable y no usa RequestBody para GET
    @GetMapping("/verificar/{usuarioId}")
    public ResponseEntity<Boolean> verificarMembresia(@PathVariable Integer usuarioId) {
        boolean tieneMembresia = membresiaService.tieneMembresiaVigentePorUsuarioId(usuarioId);
        return ResponseEntity.ok(tieneMembresia);
    }

    @PostMapping("/cancelar")
    public ResponseEntity<Void> cancelarMembresia(@RequestBody User usuario) {
        membresiaService.desactivarMembresia(usuario);
        return ResponseEntity.noContent().build();
    }
}
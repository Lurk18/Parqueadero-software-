package com.example.demo.Parqueadero;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comunidad-uis")
@RequiredArgsConstructor

public class ComunidadUISController {

    private final ComunidadUISService comunidadUISService;

    @PostMapping("/crear")
    public ResponseEntity<ComunidadUIS> crearComunidadUIS(@RequestBody Map<String, Object> body) {
        Integer codigoUis = (Integer) body.get("codigoUis");
        Integer usuarioId = (Integer) body.get("usuarioId");

        ComunidadUIS comunidadUIS = comunidadUISService.crearComunidadUIS(codigoUis, usuarioId);
        return ResponseEntity.ok(comunidadUIS);
    }

    @GetMapping("/verificar/{usuarioId}")
    @PreAuthorize("hasRole('administrador')")
    public ResponseEntity<Boolean> verificarComunidadUIS(@PathVariable Integer usuarioId) {
        boolean pertenece = comunidadUISService.esUsuarioDeComunidadUIS(usuarioId);
        return ResponseEntity.ok(pertenece);
    }
}

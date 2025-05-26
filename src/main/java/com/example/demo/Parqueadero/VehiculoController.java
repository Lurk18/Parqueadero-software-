package com.example.demo.Parqueadero;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculo")
@RequiredArgsConstructor
@PreAuthorize("hasRole('administrador')")
public class VehiculoController {

    private final VehiculoRepository vehiculoRepository;
    private final UserRepository userRepository;
    private final RegistroRepository registroRepository; //

    // Crear vehículo
    @PostMapping("/registrar")
    public ResponseEntity<Vehiculo> registrarVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoRepository.save(vehiculo);
        return ResponseEntity.ok(nuevoVehiculo);
    }

    // Listar todos los vehículos
    @GetMapping
    public ResponseEntity<List<Vehiculo>> listarVehiculos() {
        List<Vehiculo> lista = vehiculoRepository.findAll();
        return ResponseEntity.ok(lista);
    }

    // Obtener vehículo por id
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerVehiculo(@PathVariable Integer id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));
        return ResponseEntity.ok(vehiculo);
    }

    // Actualizar vehículo
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(@PathVariable Integer id, @RequestBody Vehiculo vehiculoActualizado) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        vehiculo.setPlaca(vehiculoActualizado.getPlaca());
        vehiculo.setTipo(vehiculoActualizado.getTipo());
        vehiculo.setColor(vehiculoActualizado.getColor());
        vehiculo.setUsuario(vehiculoActualizado.getUsuario());

        Vehiculo actualizado = vehiculoRepository.save(vehiculo);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar vehículo con sus registros asociados
    @DeleteMapping("/{id}")
    @Transactional  // Opcional, pero recomendable para que todo borre en una sola transacción
    public ResponseEntity<String> eliminarVehiculo(@PathVariable Integer id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        // Usar la instancia inyectada, no la clase
        List<Registro> registros = registroRepository.findByVehiculoId(id);
        if (!registros.isEmpty()) {
            registroRepository.deleteAll(registros);
        }

        vehiculoRepository.delete(vehiculo);

        return ResponseEntity.ok("Vehículo y sus registros fueron eliminados correctamente");
    }
}

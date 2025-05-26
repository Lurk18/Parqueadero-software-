package com.example.demo.Parqueadero;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final TarifaRepository tarifaRepository;
    private final ParqueaderoRepository parqueaderoRepository;
    private final VehiculoRepository vehiculoRepository;

    public Registro registrarEntrada(Registro registro) {
        // 1. Validar y obtener el parqueadero desde la BD
        Parqueadero parqueadero = parqueaderoRepository.findById(registro.getParqueadero().getId())
                .orElseThrow(() -> new RuntimeException("Parqueadero no encontrado"));

        if (!parqueadero.getDisponible()) {
            throw new RuntimeException("El parqueadero ya está ocupado");
        }

        // 2. Validar y obtener el vehículo desde la BD
        if (registro.getVehiculo() == null || registro.getVehiculo().getId() == null) {
            throw new RuntimeException("Debe proporcionar un ID de vehículo válido");
        }

        Vehiculo vehiculo = vehiculoRepository.findById(registro.getVehiculo().getId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        // 3. Marcar parqueadero como ocupado
        parqueadero.setDisponible(false);
        parqueaderoRepository.save(parqueadero);

        // 4. Asignar los objetos reales al registro
        registro.setParqueadero(parqueadero);
        registro.setVehiculo(vehiculo);
        if (registro.getFechaEntrada() == null) {
            registro.setFechaEntrada(LocalDateTime.now());
        }

        return registroRepository.save(registro);
    }

    public BigDecimal registrarSalida(Integer id) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));

        if (!registro.estaActivo()) {
            throw new RuntimeException("Este vehículo ya fue marcado como salido.");
        }

        registro.finalizarRegistro();

        Duration duracion = Duration.between(registro.getFechaEntrada(), registro.getFechaSalida());
        long minutos = duracion.toMinutes();
        BigDecimal horas = BigDecimal.valueOf(Math.max(1, Math.ceil(minutos / 60.0)));

        String tipoVehiculo = registro.getVehiculo().getTipo();
        if (tipoVehiculo == null) {
            throw new RuntimeException("El tipo de vehículo es nulo. Verifica si se cargó correctamente.");
        }

        Tarifa tarifa = tarifaRepository.findByTipoVehiculo(tipoVehiculo)
                .orElseThrow(() -> new RuntimeException("No se encontró tarifa para el tipo: " + tipoVehiculo));

        BigDecimal tarifaPorHora = tarifa.getTarifaHora();
        BigDecimal total = tarifaPorHora.multiply(horas, new MathContext(2));

        registroRepository.save(registro);

        Parqueadero parqueadero = registro.getParqueadero();
        if (!parqueadero.getDisponible()) {
            parqueadero.setDisponible(true);
            parqueaderoRepository.save(parqueadero);
        } else {
            throw new RuntimeException("El parqueadero ya estaba disponible. Error en la lógica de salida.");
        }

        return total;
    }
}

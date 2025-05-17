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

    public Registro registrarEntrada(Registro registro) {
        if (registro.getFechaEntrada() == null) {
            registro.setFechaEntrada(LocalDateTime.now());
        }
        return registroRepository.save(registro);
    }

    public BigDecimal registrarSalida(Integer id) {
        // 🔹 1️⃣ Buscar el registro
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));

        // 🔹 2️⃣ Verificar si ya está finalizado
        if (!registro.estaActivo()) {
            throw new RuntimeException("Este vehículo ya fue marcado como salido.");
        }

        // 🔹 3️⃣ Finalizar el registro (asigna la fecha de salida)
        registro.finalizarRegistro();

        // 🔹 4️⃣ Calcular duración en minutos
        Duration duracion = Duration.between(registro.getFechaEntrada(), registro.getFechaSalida());
        long minutos = duracion.toMinutes();

        // 🔹 5️⃣ Calcular el tiempo en horas (mínimo 1 hora facturable)
        BigDecimal horas = BigDecimal.valueOf(Math.max(1, Math.ceil(minutos / 60.0)));

        // 🔹 6️⃣ Buscar la tarifa correspondiente al tipo de vehículo
        String tipoVehiculo = registro.getVehiculo().getTipo();
        Tarifa tarifa = tarifaRepository.findByTipoVehiculo(tipoVehiculo)
                .orElseThrow(() -> new RuntimeException("No se encontró tarifa para el tipo: " + tipoVehiculo));

        // 🔹 7️⃣ Calcular el costo total
        BigDecimal tarifaPorHora = tarifa.getTarifaHora();
        BigDecimal total = tarifaPorHora.multiply(horas, new MathContext(2));

        // 🔹 8️⃣ Guardar la actualización en la BD
        registroRepository.save(registro);

        // 🔹 9️⃣ Marcar el parqueadero como disponible SOLO SI estaba ocupado
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
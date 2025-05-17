package com.example.demo.Parqueadero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParqueaderoService {

    @Autowired
    private ParqueaderoRepository parqueaderoRepository;

    public List<Parqueadero> obtenerDisponibles() {
        return parqueaderoRepository.findByDisponibleTrue();
    }

    public List<Parqueadero> obtenerTodos() {
        return parqueaderoRepository.findAll();
    }

    public void actualizarDisponibilidad(Integer id, boolean disponible) {
        Parqueadero parqueadero = parqueaderoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parqueadero no encontrado"));
        parqueadero.setDisponible(disponible);
        parqueaderoRepository.save(parqueadero);
    }
}

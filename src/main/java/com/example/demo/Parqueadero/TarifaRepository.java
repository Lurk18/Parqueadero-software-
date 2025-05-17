package com.example.demo.Parqueadero;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
    Optional<Tarifa> findByTipoVehiculo(String tipoVehiculo);
}

package com.example.demo.Parqueadero;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroRepository extends JpaRepository<Registro, Integer> {
    List<Registro> findByVehiculoId(Integer vehiculoId);
}

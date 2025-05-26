package com.example.demo.Parqueadero;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    List<Vehiculo> findByPlaca(String placa);

}

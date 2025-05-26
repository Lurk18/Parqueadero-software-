package com.example.demo.Parqueadero;

import com.example.demo.User.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MembresiaService {
    private final MembresiaRepository membresiaRepository;

    public MembresiaService(MembresiaRepository membresiaRepository) {
        this.membresiaRepository = membresiaRepository;
    }
    public boolean tieneMembresiaVigentePorUsuarioId(Integer usuarioId) {
        return membresiaRepository.findByUsuario_Id(usuarioId)
                .map(Membresia::isVigente)
                .orElse(false);
    }


    // Método para crear o renovar la membresía
    public Membresia crearORenovarMembresia(User usuario) {
        Membresia membresia = membresiaRepository.findByUsuario_Id(usuario.getId())
                .orElse(new Membresia());

        membresia.setUsuario(usuario);
        membresia.renovar();
        return membresiaRepository.save(membresia);
    }

    // Método para verificar si la membresía está vigente (fecha y flag)
    public boolean tieneMembresiaVigente(User usuario) {
        return membresiaRepository.findByUsuario_Id(usuario.getId())
                .map(m -> m.isVigente() &&
                        !LocalDate.now().isBefore(m.getFechaInicio()) &&
                        !LocalDate.now().isAfter(m.getFechaFin()))
                .orElse(false);
    }

    // Método para cancelar la membresía
    public void desactivarMembresia(User usuario) {
        membresiaRepository.findByUsuario_Id(usuario.getId())
                .ifPresent(membresia -> {
                    membresia.cancelar();
                    membresiaRepository.save(membresia);
                });
    }
}

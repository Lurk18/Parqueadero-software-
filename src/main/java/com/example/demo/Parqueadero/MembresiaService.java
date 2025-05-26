package com.example.demo.Parqueadero;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MembresiaService {
    @Autowired
    private MembresiaRepository membresiaRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean usuarioTieneMembresia(Integer userId) {
        return membresiaRepository.findByUsuario_Id(userId).isPresent();
    }

    public boolean esMembresiaVigente(Integer userId) {
        return obtenerMembresiaDeUsuario(userId)
                .map(Membresia::getVigente)
                .orElse(false);
    }

    public Membresia crearMembresiaParaUsuario(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioTieneMembresia(userId)) {
            throw new RuntimeException("El usuario ya tiene una membresía");
        }

        Membresia membresia = new Membresia(user);
        return membresiaRepository.save(membresia);
    }

    public Optional<Membresia> obtenerMembresiaDeUsuario(Integer userId) {
        return membresiaRepository.findByUsuario_Id(userId);
    }
}

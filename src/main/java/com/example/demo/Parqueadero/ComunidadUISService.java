package com.example.demo.Parqueadero;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ComunidadUISService {
    private final ComunidadUISRepository comunidadUISRepository;
    private final UserRepository userRepository;

    public ComunidadUISService(ComunidadUISRepository comunidadUISRepository, UserRepository userRepository) {
        this.comunidadUISRepository = comunidadUISRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ComunidadUIS crearComunidadUIS(Integer codigoUis, Integer usuarioId) {
        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (comunidadUISRepository.findByUsuario_Id(usuarioId).isPresent()) {
            throw new IllegalStateException("El usuario ya pertenece a la comunidad UIS");
        }

        ComunidadUIS comunidadUIS = new ComunidadUIS();
        comunidadUIS.setCodigoUIS(codigoUis);
        comunidadUIS.setUsuario(usuario);

        return comunidadUISRepository.save(comunidadUIS);
    }

    public boolean esUsuarioDeComunidadUIS(Integer usuarioId) {
        return comunidadUISRepository.findByUsuario_Id(usuarioId).isPresent();
    }
}

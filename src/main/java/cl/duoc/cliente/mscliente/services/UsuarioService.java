package cl.duoc.cliente.mscliente.services;

import cl.duoc.cliente.mscliente.model.Usuario;
import cl.duoc.cliente.mscliente.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuariorepository;

    public List<Usuario> buscarUsuarios() {
        return usuariorepository.findAll();

    }

    public Usuario buscarIdUsuario(String id_usuario) {
        return usuariorepository.findById(id_usuario).orElse(null);
    }

    public Usuario registrarUsuario(Usuario usuario) {
        return usuariorepository.save(usuario);
    }

    public Usuario cambiarEstadoUsuario(String id_usuario, String nuevoEstado) {
        Usuario usuario = usuariorepository.findById(id_usuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        String estadoFormateado = nuevoEstado.toUpperCase();
        // el "!" se utiliza para voltear la logica
        if (!estadoFormateado.equals("ACTIVADO") && !estadoFormateado.equals("DESACTIVADO")) {
            throw new IllegalArgumentException("No es un estado válido para el usuario");
        }


        if (estadoFormateado.equalsIgnoreCase(usuario.getEstado())) {
            throw new IllegalArgumentException("El usuario ya se encuentra " + estadoFormateado);
        }

        usuario.setEstado(estadoFormateado);
        return usuariorepository.save(usuario);
    }


        public Usuario editarUsuario(@PathVariable String id_cliente, @RequestBody Usuario usuarioEditado) {
            // Buscar el usuario por ID
            Optional<Usuario> optionalUsuario = usuariorepository.findById(id_cliente);

            if (!optionalUsuario.isPresent()) {
                throw new EntityNotFoundException("Usuario no encontrado");
            }

            Usuario usuario = optionalUsuario.get();

            // Actualizar campos (validar si los datos son null si quieres edición parcial)
            usuario.setNombre(usuarioEditado.getNombre());
            usuario.setApellido(usuarioEditado.getApellido());
            usuario.setEmail(usuarioEditado.getEmail());
            usuario.setTelefono(usuarioEditado.getTelefono());
            usuario.setDireccion(usuarioEditado.getDireccion());
            usuario.setPassword(usuarioEditado.getPassword());


            return usuariorepository.save(usuarioEditado);
        }
    }




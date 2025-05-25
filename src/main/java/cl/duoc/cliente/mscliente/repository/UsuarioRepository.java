package cl.duoc.cliente.mscliente.repository;

import cl.duoc.cliente.mscliente.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}

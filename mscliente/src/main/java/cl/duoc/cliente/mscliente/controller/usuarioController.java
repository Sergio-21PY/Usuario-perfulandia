package cl.duoc.cliente.mscliente.controller;

import cl.duoc.cliente.mscliente.model.Usuario;
import cl.duoc.cliente.mscliente.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/v1/usuarios")
@RestController

 /*
    #No puedo eliminar a los usuarios ya que se encuentran con FK de ventas, lo cual haria que se eliminaran las ventas y una sucursal necesita saber que cosas de venden,
    en caso de que el usuario no quiera seguir con una cuenta solamente se desactiva
 */

public class usuarioController {

    @Autowired
    private UsuarioService usuarioservice;

    @GetMapping
    //Busca todos los usuarios registrados
    public ResponseEntity<List<Usuario>> ListarUsuarios() {
        List<Usuario> usuarios = this.usuarioservice.buscarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay registro de usuarios");
            return ResponseEntity.ok().body(List.of());
        } else {
            System.out.println("Listado de usuarios");
            return ResponseEntity.ok(usuarios);
        }

    }

    @GetMapping("/{id_usuario}")
    public ResponseEntity<?> buscarId(@PathVariable String id_usuario) {
        try {
            Usuario usuario = usuarioservice.buscarIdUsuario(id_usuario);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado en la base de datos");
            } else {
                return ResponseEntity.ok(usuario);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado" + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> registrarUsuario(@Validated @RequestBody Usuario usuario) {
        try{
            Usuario usuarioRegistrado;

            try{
                usuarioRegistrado = this.usuarioservice.buscarIdUsuario(usuario.getId_cliente());


            } catch (Exception v4r) { //2do Try
                usuarioRegistrado = null;
            }
            if (usuarioRegistrado == null) {
                if (usuario.getId_cliente() == null){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id del cliente no puede ser nulo");
                }
                else{
                    this.usuarioservice.registrarUsuario(usuario);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente");
                }
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El cliende con el ID" + usuarioRegistrado.getId_cliente() +"Ya existe en la base de datos");
            }

        } catch (Exception e) {//1er Try
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado" + e.getMessage());
        }
    }

    // http://localhost:8080/api/v1/usuarios/id_usuario/estado?nuevoEstado="ACTIVADO" - "DESACTIVADO"
    @PutMapping("/{id_usuario}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable("id_usuario") String id_usuario,
                                           @RequestParam("nuevoEstado") String nuevoEstado) {
        try {
            Usuario usuarioActualizado = usuarioservice.cambiarEstadoUsuario(id_usuario, nuevoEstado);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            // Retornamos un mensaje si ya tiene ese estado
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


        @PutMapping("/{id}")
        public ResponseEntity<?> editarUsuario(@PathVariable String id,
                                               @RequestBody Usuario usuarioEditado) {
            try {
                Usuario actualizado = usuarioservice.editarUsuario(id, usuarioEditado);
                return ResponseEntity.ok(actualizado);
            } catch (EntityNotFoundException e) {
                return ResponseEntity.notFound().build();
            }
        }
    }



















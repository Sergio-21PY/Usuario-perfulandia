package cl.duoc.cliente.mscliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLIENTE")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id
    @Column(name = "id_cliente")
    private String id_cliente;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int telefono;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String estado;


}

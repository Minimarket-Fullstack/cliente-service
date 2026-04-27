package com.minimarket.cliente_service.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 13, nullable = false)
    //@NotBlank(message = "EL RUT ES OBLIGATORIO")
    private String rut;

    @Column(nullable = false)
    //@NotBlank(message = "EL NOMBRE ES OBLIGATORIO")
    private String nombre;

    @Column(nullable = false)
    //@NotBlank(message = "EL APELLIDO ES OBLIGATORIO")
    private String apellido;

    @Column(nullable = false, unique = true)
    //@Email(message = "EL EMAIL DEBE SER VÁLIDO")
    private String email;

    //borrado lógico
    private boolean activo = true;

}

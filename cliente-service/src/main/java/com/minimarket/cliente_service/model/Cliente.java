package com.minimarket.cliente_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.DataAmount;
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

    @NotBlank
    @Column(unique = true, length = 13, nullable = false)
    @NotBlank(message = "EL RUT ES OBLIGATORIO")
    private String rut;

    @Column(nullable = false)
    @NotBlank(message = "EL NOMBRE ES OBLIGATORIO")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "EL APELLIDO ES OBLIGATORIO")
    private String apellido;

    @Column(nullable = false, unique = true)
    @Email(message = "EL EMAIL DEBE SER VÁLIDO")
    private String email;

    @Column(nullable = false)
    private boolean activo = true;








}

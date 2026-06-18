package com.minimarket.cliente_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteUpdateDTO {

    @NotBlank(message="EL NOMBRE ES OBLIGATORIO")
    private String nombre;

    @NotBlank(message="EL APELLIDO ES OBLIGATORIO")
    private String apellido;

    @NotBlank(message="EL EMAIL ES OBLIGATORIO")
    @Email(message = "El EMAIL DEBE SER VÁLIDO")
    private String email;
}

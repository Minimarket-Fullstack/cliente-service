package com.minimarket.cliente_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//entrada
public class ClienteRequestDTO {

    @NotBlank(message = "EL RUT ES OBLIGATORIO")
    private String rut;

    @NotBlank(message = "EL NOMBRE ES OBLIGATORIO")
    private String nombre;

    @NotBlank(message = "EL APELLIDO ES OBLIGATORIO")
    private String apellido;

    @NotBlank(message="EL EMAIL ES OBLIGATORIO")
    @Email(message = "EL EMAIL DEBE SER VÁLIDO")
    private String email;
}

package com.minimarket.cliente_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//salida
public class ClienteResponseDTO {

    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
}

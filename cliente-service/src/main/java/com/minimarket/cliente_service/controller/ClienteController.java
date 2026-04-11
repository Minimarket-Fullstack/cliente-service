package com.minimarket.cliente_service.controller;

import com.minimarket.cliente_service.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    //AQUÍ VA EL CRUD, APLICAR REGLA DE NEGOCIO
    //VALIDACIONES BINDINGRESULT Y TRYCATCH
    //PARA QUE QUEDE LARAJA
}

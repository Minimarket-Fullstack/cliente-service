package com.minimarket.cliente_service.controller;

import com.minimarket.cliente_service.model.Cliente;
import com.minimarket.cliente_service.service.ClienteService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;


    @GetMapping
    public ResponseEntity<?> listarClientes(){
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }






}

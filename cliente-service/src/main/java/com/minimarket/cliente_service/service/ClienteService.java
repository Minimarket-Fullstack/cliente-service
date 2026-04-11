package com.minimarket.cliente_service.service;

import com.minimarket.cliente_service.model.Cliente;
import com.minimarket.cliente_service.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }
}

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
        return clienteRepository.findByActivoTrue();
        // para q no liste los inactivos también
    }
                                                                            //.get Claude me reviso el código
                                                                            // y me dijo q era mejor ponerle orelse, pq
                                                                            //sino me retorna un 500 y lo quiero mantener con lo q tengo en el controller
    public Cliente findById(long id){ return clienteRepository.findById(id).orElse(null);}

    public Cliente save(Cliente cliente){ return clienteRepository.save(cliente);}

    public boolean existePorRut(String rut){
        return clienteRepository.findByRut(rut).isPresent();
    }

    public Cliente findByRut(String rut){
        return clienteRepository.findByRut(rut).orElse(null);
    }

    //Probando cosas thro new runtimexception
    public Cliente desactivarCliente(Long id, String rut) {
        Cliente clienteId = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("ID NO ENCONTRADO")));

        if (!clienteId.getRut().equals(rut)) {
            throw new RuntimeException("EL RUT INGRESADO NO CORRESPONDE AL ID DEL CLIENTE");
        }

        clienteId.setActivo(false);

        return clienteRepository.save(clienteId);
    }



}

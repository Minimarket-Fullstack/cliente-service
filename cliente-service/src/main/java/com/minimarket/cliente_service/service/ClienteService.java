package com.minimarket.cliente_service.service;

import com.minimarket.cliente_service.dto.ClienteRequestDTO;
import com.minimarket.cliente_service.dto.ClienteResponseDTO;
import com.minimarket.cliente_service.model.Cliente;
import com.minimarket.cliente_service.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private ClienteResponseDTO mapToDTO(Cliente cliente){
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getRut(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail()
        );
    }



    public List<ClienteResponseDTO> obtenerTodos(){
        return clienteRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<ClienteResponseDTO> obtenerPorId(Long id){
        return clienteRepository.findById(id).map(this::mapToDTO);
    }

    public ClienteResponseDTO guardar(ClienteRequestDTO dto){
        Cliente cliente = new Cliente(null, dto.getRut(),dto.getNombre(), dto.getApellido(), dto.getEmail());
        return mapToDTO(clienteRepository.save(cliente));
    }

    public Cliente save(Cliente cliente){ return clienteRepository.save(cliente);}

    public boolean existePorRut(String rut){
        return clienteRepository.findByRut(rut).isPresent();
    }

    public Cliente findByRut(String rut){
        return clienteRepository.findByRut(rut).orElse(null);
    }





}

package com.minimarket.cliente_service.service;

import com.minimarket.cliente_service.dto.ClienteRequestDTO;
import com.minimarket.cliente_service.dto.ClienteResponseDTO;
import com.minimarket.cliente_service.model.Cliente;
import com.minimarket.cliente_service.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

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
        return clienteRepository.findByActivoTrue().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<ClienteResponseDTO> obtenerPorId(Long id){
        return clienteRepository.findById(id).map(this::mapToDTO);
    }

    public ClienteResponseDTO guardar(ClienteRequestDTO dto){
        Cliente cliente = new Cliente(null, dto.getRut(),dto.getNombre(), dto.getApellido(), dto.getEmail(), true);
        return mapToDTO(clienteRepository.save(cliente));
    }

    //quite el rut pq no debería ser editable
    public Optional<ClienteResponseDTO> actualizar(Long id, ClienteRequestDTO dto){
        return clienteRepository.findByIdAndActivoTrue(id).map( existente ->{
            existente.setNombre(dto.getNombre());
            existente.setApellido(dto.getApellido());
            existente.setEmail(dto.getEmail());
            return mapToDTO(clienteRepository.save(existente));
        });
    }

    public void eliminarCli(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado con el id: " + id));
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    public Optional<ClienteResponseDTO> obtenerPorRut(String rut){
        return clienteRepository.findByRut(rut).map(this::mapToDTO);
    }

    public List<ClienteResponseDTO> buscarPorNombre(String nombre){
        return clienteRepository.findByNombreContainingIgnoreCase(nombre).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}

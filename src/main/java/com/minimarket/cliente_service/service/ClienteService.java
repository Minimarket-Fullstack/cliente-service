package com.minimarket.cliente_service.service;

import com.minimarket.cliente_service.dto.ClienteRequestDTO;
import com.minimarket.cliente_service.dto.ClienteResponseDTO;
import com.minimarket.cliente_service.dto.ClienteUpdateDTO;
import com.minimarket.cliente_service.exception.ClienteNotFoundException;
import com.minimarket.cliente_service.model.Cliente;
import com.minimarket.cliente_service.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClienteService {

    //private static final Logger logss = LoggerFactory.getLogger(ClienteService.class);
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
        return clienteRepository.findByIdAndActivoTrue(id).map(this::mapToDTO);
    }

    public ClienteResponseDTO guardar(ClienteRequestDTO dto){
        log.info("Guardando cliente con el nombre: {}", dto.getNombre());
        Cliente cliente = new Cliente(null, dto.getRut(),dto.getNombre(), dto.getApellido(), dto.getEmail(), true);
        return mapToDTO(clienteRepository.save(cliente));
    }

    //quite el rut pq no debería ser editablez
    //Actualizar independiente de q el cliente este activo ono
    public Optional<ClienteResponseDTO> actualizar(Long id, ClienteUpdateDTO dto){
        return clienteRepository.findByIdAndActivoTrue(id).map( existente ->{
            existente.setNombre(dto.getNombre());
            existente.setApellido(dto.getApellido());
            existente.setEmail(dto.getEmail());
            return mapToDTO(clienteRepository.save(existente));
        });
    }

    //el runetimeException agarra retoran un 500, voy a tener q hacer una exsception de q el cliente no see ncontro
    public void eliminarCli(Long id){
        Cliente cliente = clienteRepository
                .findById(id)           //responsestatusexception podría ser en vez de runtime exception
                //el globalexceptionhandlern no pasa por acá
                .orElseThrow(() -> new ClienteNotFoundException(id));

        //necesito q si el cliente no esta activo, me retorne que ya fue eliminado, no un 500
        if(!cliente.isActivo()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya se encuentra eliminado."); // un 409
        }
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    public Optional<ClienteResponseDTO> obtenerPorRut(String rut){
        return clienteRepository.findByRut(rut).map(this::mapToDTO);
    }

    public List<ClienteResponseDTO> buscarPorNombre(String nombre){
        return clienteRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}

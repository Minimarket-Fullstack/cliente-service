package com.minimarket.cliente_service.service;

import com.minimarket.cliente_service.client.NotificacionClient;
import com.minimarket.cliente_service.dto.ClienteRequestDTO;
import com.minimarket.cliente_service.dto.ClienteResponseDTO;
import com.minimarket.cliente_service.dto.ClienteUpdateDTO;
import com.minimarket.cliente_service.dto.NotificacionRequestDTO;
import com.minimarket.cliente_service.exception.ClienteNotFoundException;
import com.minimarket.cliente_service.model.Cliente;
import com.minimarket.cliente_service.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ClienteRepository clienteRepository;
    private final NotificacionClient notificacionClient;

    private ClienteResponseDTO mapToDTO(Cliente cliente){
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getRut(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail()
        );
    }

    //método para mandar notificaciones

    private void notificar(String tipo, String mensaje){
        try{
            notificacionClient.enviarNotificacion(new NotificacionRequestDTO(tipo,mensaje));

            log.info("NOTIFICACION ENVIADA: TIPO: {}",tipo);

        }catch (Exception e){
            log.warn("NO SE PUDO ENVIAR LA NOTIFICACIÓN - TIPO {} : {}", tipo,e.getMessage());
        }
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
        ClienteResponseDTO responseDTO = mapToDTO(clienteRepository.save(cliente));
        //tengo q notificar
        notificar("CLIENT-CREADO", "SISTEMA: SE HA REGISTRADO CON ÉXITO AL CLIENTE " +dto.getNombre() +" " + dto.getApellido() + " RUT: " + dto.getRut());

        return responseDTO;
    }

    //quite el rut pq no debería ser editablez
    //Actualizar independiente de q el cliente este activo ono
    public Optional<ClienteResponseDTO> actualizar(Long id, ClienteUpdateDTO dto){
        return clienteRepository.findByIdAndActivoTrue(id).map( existente ->{
            existente.setNombre(dto.getNombre());
            existente.setApellido(dto.getApellido());
            existente.setEmail(dto.getEmail());



            ClienteResponseDTO responseDTO = mapToDTO(clienteRepository.save(existente));
            notificar("CLIENTE_ACTUALIZADO", "SISTEMA: SE ACTUALIZARON LOS DATOS DEL CLIENTE: " + dto.getNombre() + " " + dto.getApellido() + ".");
            return responseDTO;
        });
    }

    //el runetimeException agarra retoran un 500, voy a tener q hacer una exsception de q el cliente no see ncontro
    public void eliminarCli(Long id){
        Cliente cliente = clienteRepository
                .findByIdAndActivoTrue(id)           //responsestatusexception podría ser en vez de runtime exception
                //el globalexceptionhandlern no pasa por acá
                .orElseThrow(() -> new ClienteNotFoundException(id));

        //necesito q si el cliente no esta activo, me retorne que ya fue eliminado, no un 500
        if(!cliente.isActivo()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya se encuentra eliminado."); // un 409
        }
        cliente.setActivo(false);
        clienteRepository.save(cliente);

        notificar("CLIENTE ELIMINADO", "SISTEMA: SE ELIMINARON LOS DATOS DEL CLIENTE: " + cliente.getNombre() + " " + cliente.getApellido() + " ID: " + cliente.getId());
    }

    public Optional<ClienteResponseDTO> obtenerPorRut(String rut){
        return clienteRepository.findByRutAndActivoTrue(rut).map(this::mapToDTO);
    }

    public List<ClienteResponseDTO> buscarPorNombre(String nombre){
        return clienteRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}

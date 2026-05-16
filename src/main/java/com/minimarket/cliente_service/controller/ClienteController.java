package com.minimarket.cliente_service.controller;

import com.minimarket.cliente_service.dto.ClienteRequestDTO;
import com.minimarket.cliente_service.dto.ClienteResponseDTO;
import com.minimarket.cliente_service.dto.ClienteUpdateDTO;
import com.minimarket.cliente_service.exception.ClienteNotFoundException;
import com.minimarket.cliente_service.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {


    //private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    //Cualquier usuario autenticado puede listar
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes(){
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    //Solo estos usuarios con ese rol pu3eden ver al paciente
     @GetMapping("/{id}")
     @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable Long id){
        log.info("Buscando paciente con el id: {}", id);
        return clienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    //SOLO EL PUEDE
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDTO> crearCliente(@Valid @RequestBody ClienteRequestDTO dto){
        return ResponseEntity.status(201).body(clienteService.guardar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ClienteResponseDTO> actualizarCli(@PathVariable Long id, @Valid @RequestBody ClienteUpdateDTO dto){
        return clienteService.actualizar(id,dto).map(ResponseEntity::ok).orElseThrow(() -> new ClienteNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        clienteService.eliminarCli(id);
        return ResponseEntity.ok(Map.of("Mensaje", "Cliente eliminado correctamente"));
    }

    //Retornar en el postman un json
    @GetMapping("/rut/{rut}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> buscarPorRut(@PathVariable String rut){
        return clienteService.obtenerPorRut(rut).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<ClienteResponseDTO>> buscarPorNombre(@PathVariable String nombre){
        //y q retorno si la lista esta vacía? si la dejo así va a tirar un 200 todo el rato
        //no se si sea bueno
        List<ClienteResponseDTO> listaDto = clienteService.buscarPorNombre(nombre);
        if(!listaDto.isEmpty()){
            return ResponseEntity.ok(listaDto);
        }
        return ResponseEntity.noContent().build();// 204

    }

}

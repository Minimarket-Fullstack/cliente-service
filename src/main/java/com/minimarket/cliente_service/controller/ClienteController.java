package com.minimarket.cliente_service.controller;

import com.minimarket.cliente_service.dto.ClienteRequestDTO;
import com.minimarket.cliente_service.dto.ClienteResponseDTO;
import com.minimarket.cliente_service.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {


    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes(){
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

     @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable Long id){
        log.info("Buscando paciente con el id: {}", id);
        return clienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@Valid @RequestBody ClienteRequestDTO dto){
        return ResponseEntity.status(201).body(clienteService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCli(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto){
        return clienteService.actualizar(id,dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        //esta mal esto, pq si no lo encuentra me retorna 500
//        if(clienteService.obtenerPorId(id).isEmpty()){
//            return ResponseEntity.notFound().build();
//        }

        //lo dearriba lo manejo en el service
        clienteService.eliminarCli(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClienteResponseDTO> buscarPorRut(@PathVariable String rut){
        return clienteService.obtenerPorRut(rut).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
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

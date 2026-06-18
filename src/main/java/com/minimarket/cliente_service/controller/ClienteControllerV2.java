package com.minimarket.cliente_service.controller;

import com.minimarket.cliente_service.assemblers.ClienteModelAssembler;
import com.minimarket.cliente_service.dto.ClienteRequestDTO;
import com.minimarket.cliente_service.dto.ClienteResponseDTO;
import com.minimarket.cliente_service.dto.ClienteUpdateDTO;
import com.minimarket.cliente_service.exception.ClienteNotFoundException;
import com.minimarket.cliente_service.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes HATEOAS", description = "Endpoints de clientes con enlaces HATEOAS")
public class ClienteControllerV2 {

    private final ClienteService clienteService;
    private final ClienteModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Listar clientes con HATEOAS")
    public CollectionModel<EntityModel<ClienteResponseDTO>> listarClientes() {
        List<EntityModel<ClienteResponseDTO>> clientes = clienteService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteControllerV2.class).listarClientes()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Obtener cliente por ID con HATEOAS")
    public EntityModel<ClienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        ClienteResponseDTO cliente = clienteService.obtenerPorId(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        return assembler.toModel(cliente);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear cliente con HATEOAS")
    public ResponseEntity<EntityModel<ClienteResponseDTO>> crearCliente(@Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(201).body(assembler.toModel(clienteService.guardar(dto)));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Actualizar cliente con HATEOAS")
    public EntityModel<ClienteResponseDTO> actualizarCli(@PathVariable Long id, @Valid @RequestBody ClienteUpdateDTO dto) {
        ClienteResponseDTO cliente = clienteService.actualizar(id, dto)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        return assembler.toModel(cliente);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar cliente")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        clienteService.eliminarCli(id);
        return ResponseEntity.ok(Map.of("MENSAJE", "CLIENTE ELIMINADO CORRECTAMENTE"));
    }

    @GetMapping(value = "/rut/{rut}", produces = MediaTypes.HAL_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Buscar cliente por RUT con HATEOAS")
    public EntityModel<ClienteResponseDTO> buscarPorRut(@PathVariable String rut) {
        ClienteResponseDTO cliente = clienteService.obtenerPorRut(rut)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con RUT: " + rut));
        return assembler.toModel(cliente);
    }

    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Buscar clientes por nombre con HATEOAS")
    public CollectionModel<EntityModel<ClienteResponseDTO>> buscarPorNombre(@PathVariable String nombre) {
        List<EntityModel<ClienteResponseDTO>> clientes = clienteService.buscarPorNombre(nombre).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteControllerV2.class).buscarPorNombre(nombre)).withSelfRel(),
                linkTo(methodOn(ClienteControllerV2.class).listarClientes()).withRel("clientes"));
    }
}

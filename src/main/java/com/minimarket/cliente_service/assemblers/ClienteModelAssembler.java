package com.minimarket.cliente_service.assemblers;

import com.minimarket.cliente_service.controller.ClienteControllerV2;
import com.minimarket.cliente_service.dto.ClienteResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteResponseDTO, EntityModel<ClienteResponseDTO>> {

    @Override
    public EntityModel<ClienteResponseDTO> toModel(ClienteResponseDTO cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteControllerV2.class).obtenerPorId(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteControllerV2.class).listarClientes()).withRel("clientes"),
                linkTo(methodOn(ClienteControllerV2.class).buscarPorRut(cliente.getRut())).withRel("cliente-por-rut"),
                linkTo(methodOn(ClienteControllerV2.class).buscarPorNombre(cliente.getNombre())).withRel("clientes-por-nombre"));
    }
}

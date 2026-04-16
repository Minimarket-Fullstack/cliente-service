package com.minimarket.cliente_service.controller;

import com.minimarket.cliente_service.model.Cliente;
import com.minimarket.cliente_service.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    //AQUÍ VA EL CRUD, APLICAR REGLA DE NEGOCIO
    //VALIDACIONES BINDINGRESULT Y TRYCATCH
    //PARA QUE QUEDE LARAJA
    @GetMapping
    public ResponseEntity<?> listarClientes(){
        try{
            List<Cliente> listaClientes =  clienteService.findAll();

            return ResponseEntity.ok(listaClientes);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un problema al listar los clientes");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }



    @PostMapping
    public ResponseEntity<?> guardarCliente(@Valid @RequestBody Cliente cliente, BindingResult result){
        try {

            if(result.hasErrors()){
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(error -> errores.put(error.getField(),error.getDefaultMessage()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
            }

            Cliente cliente1 = clienteService.save(cliente);

            return ResponseEntity.status(HttpStatus.CREATED).body(cliente1);


        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al intentar guardar al cliente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente, BindingResult result){
        try{

            if(result.hasErrors()){
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(error -> errores.put(error.getField(),error.getDefaultMessage()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
            }

            Cliente clienteAutentico = clienteService.findById(id);

            if(clienteAutentico == null){
                Map<String, String> error = new HashMap<>();
                error.put("error", "No se encontró el cliente.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            //no puedo cambiar el rut ni el id, pq son unicoooos
            clienteAutentico.setNombre(cliente.getNombre());
            clienteAutentico.setApellido(cliente.getApellido());
            clienteAutentico.setEmail(cliente.getEmail());

            Cliente clienteActualizado = clienteService.save(clienteAutentico);

            return ResponseEntity.ok(clienteActualizado);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar al cliente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @DeleteMapping("/eliminar/{id}/{rut}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id, @PathVariable String rut){
        try{
            Cliente cliente = clienteService.findById(id);

            if (cliente == null){
                Map<String, String> error = new HashMap<>();
                error.put("error", "No se encontró el cliente que desea eliminar");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            cliente.setActivo(false);
            clienteService.desactivarCliente(id, rut);
            clienteService.save(cliente);

            Map<String, String> exito = new HashMap<>();
            exito.put("mensaje", "Cliente desactivado correctamente.");
            return ResponseEntity.ok(exito);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al intentar procesar la solicitud");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<?> buscarPorRut(@PathVariable String rut){
        try{

            Cliente cliente = clienteService.findByRut(rut);
            if (cliente == null || !cliente.isActivo()){
                Map<String, String> error = new HashMap<>();
                error.put("error", "El cliente no se encontró o se encuentra inactivo");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            return ResponseEntity.ok(cliente);

        }catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un problema al buscar el cliente por su RUT");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    // este endpoint esta pensado para q otro microservicio
    //valide q existe, no para traer el objeto completo en el json
    @GetMapping("/{id}/existe")
    public ResponseEntity<?> findClienteById(@PathVariable Long id){
        try{
            Cliente clienteEncontrado = clienteService.findById(id);

            if(clienteEncontrado == null){
                Map<String, String> error = new HashMap<>();
                error.put("error", "cliente no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            if(!clienteEncontrado.isActivo()){
                Map<String, String> error = new HashMap<>();
                error.put("error", "cliente se encuentra inactivo");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            return ResponseEntity.ok(true);


        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un problema al buscar el cliente por su ID");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }




}

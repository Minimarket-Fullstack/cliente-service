package com.minimarket.cliente_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
//agregar logs al global exceptionHandler
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleClienteNotFound(ClienteNotFoundException exception){
        log.warn("CLIENTE NO ENCONTRADO: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ERROR", "CLIENTE NO ENCONTRADO", "DETALLE", exception.getMessage()));
    }

    //Spring evalua los handlesr mas específico a más general
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errores.put(error.getField(),error.getDefaultMessage()));
        log.warn("[VALIDACIÓN] Petición rechazada. Campos con error: {}", ex.toString());
        //400 y le muestro un mapa de los errores.
        return ResponseEntity.badRequest().body(errores);
    }

    //rut duplicadoo. unique true rut
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleDuplicate(DataIntegrityViolationException dx){
        log.error("RESTRICCIÓN DE CONSTRAINT: {}", dx); //bd
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("ERROR", "El rut o el email ya estan registrados."));
    }

    //RECURSO NO ENCONTRADO
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NoSuchElementException ex){
        log.warn("NOT FOUND: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ERROR", "RECURSO NO ENCONTRADO", "DETALLE", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex){
        log.warn("OCURRIÓ UN ERROR INESPERADO: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ERROR", "ERROR INTERNO EN EL SERVIDOR"));
    }

}

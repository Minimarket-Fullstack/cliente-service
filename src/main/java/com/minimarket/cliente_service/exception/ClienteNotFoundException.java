package com.minimarket.cliente_service.exception;

public class ClienteNotFoundException extends RuntimeException{
    public ClienteNotFoundException(Long id){
        super("CLIENTE CON EL ID " + id + " NO ENCONTRADO");
    }
}

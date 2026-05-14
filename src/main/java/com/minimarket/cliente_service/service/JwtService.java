package com.minimarket.cliente_service.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final long JWT_EXPIRATION = 86400000L;



}

package com.minimarket.cliente_service.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final long JWT_EXPIRATION = 86400000L;

    public String generarToken(UserDetails usuario){
        return Jwts.builder()
        .subject(usuario.getUsername())
        .claim("roles", usuario.getAuthorities()).issuedat(new Date())
        .expiration(new Date(System.currentTimeMIllis() + JWT_EXPIRATION))
        .signWith(getSigningKey()).compact();
    }

    public String extraerUsername(String token){
        return Jwts.parser().verifyWith(GetSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }



}

package com.minimarket.cliente_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
//genera y valida los tokesn
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long JWT_EXPIRATION = 86400000L;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //genero el token para  un usuari oautenticado
    public String generarToken(UserDetails usuario){
        return Jwts.builder().subject(usuario.getUsername())
                .claim("roles",
                        usuario.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION)).signWith(getSigningKey()).compact();
    }

    //extraigo el username del token
    public String extraerUsername(String token){
        return Jwts.parser().
                verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validarToken(String token, UserDetails usuario){
        String username = extraerUsername(token);
        //data expiration
        Date expiration = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getExpiration();

        return username.equalsIgnoreCase(usuario.getUsername()) && expiration.after(new Date());

    }

}

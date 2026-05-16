package com.minimarket.cliente_service.controller;

import com.minimarket.cliente_service.dto.LoginRequestDTO;
import com.minimarket.cliente_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //Post /auth/login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO req){

        try{
            //DELEGAR LA AUTENTITACIÓN SPRING SECURITY
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));

            //Si llegamos acá, las credenciales son correctas
            UserDetails usuario = userDetailsService.loadUserByUsername(req.getUsername());

            //genero el jwt
            String token = jwtService.generarToken(usuario);

            //deuvelvo el token al cliente
        return ResponseEntity.ok(Map.of("TOKEN", token));

        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("ERROR", "CREDENCIALES INCORRECTAS"));
        }

    }

}

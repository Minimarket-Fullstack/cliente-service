package com.minimarket.cliente_service.config;

import com.minimarket.cliente_service.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
//Intercepta cada petición
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //lee el header authorization
        String authHeader = request.getHeader("Authorization");

        //si no hay ninguna de estas, continau sin autenticar.
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //extrae el token y le quita el bearer
        String jwt = authHeader.substring(7);

        //Extrae el username del token
        String username = jwtService.extraerUsername(jwt);

        //Si el hay username y el contexto de seguridad esta vacío
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails usuario = userDetailsService.loadUserByUsername(username);
            if(!jwtService.validarToken(jwt, usuario)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no válido");
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request,response);
    }
}

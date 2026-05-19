package com.minimarket.cliente_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(){

        //SOLO PARA PRUEBAS
        UserDetails usuario = User.withUsername("admin").password(passwordEncoder().encode("password1234")).roles("ADMIN").build();

        UserDetails user = User.withUsername("user").password(passwordEncoder().encode("password12345")).roles("USER").build();
        return new InMemoryUserDetailsManager(usuario, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

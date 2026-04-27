package com.minimarket.cliente_service.repository;

import com.minimarket.cliente_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByActivoTrue();

    Optional<Cliente> findByRut(String rut);

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    Optional<Cliente> findByIdAndActivoTrue(Long id);

}

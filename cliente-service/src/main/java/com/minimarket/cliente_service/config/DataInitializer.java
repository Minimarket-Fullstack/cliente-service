package com.minimarket.cliente_service.config;

import com.minimarket.cliente_service.model.Cliente;
import com.minimarket.cliente_service.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ClienteRepository clienteRepository;


    @Override
    public void run(String... args) {
        if(clienteRepository.count()>0){
            log.info(">>> DataInitalizer: La BD ya tiene datos, se omite la carga incial");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando 20 clientes de prueba...");


        clienteRepository.save(new Cliente(null, "12345678-9", "Juan", "Pérez", "juan.perez@gmail.com"));
        clienteRepository.save(new Cliente(null, "98765432-1", "María", "González", "maria.gonzalez@gmail.com"));
        clienteRepository.save(new Cliente(null, "11111111-1", "Carlos", "Rodríguez", "carlos.rodriguez@gmail.com"));
        clienteRepository.save(new Cliente(null, "22222222-2", "Ana", "Martínez", "ana.martinez@gmail.com"));
        clienteRepository.save(new Cliente(null, "33333333-3", "Pedro", "López", "pedro.lopez@gmail.com"));
        clienteRepository.save(new Cliente(null, "44444444-4", "Valentina", "Soto", "vale.soto@gmail.com"));
        clienteRepository.save(new Cliente(null, "55555555-5", "Diego", "Fuentes", "diego.fuentes@gmail.com"));
        clienteRepository.save(new Cliente(null, "66666666-6", "Camila", "Vargas", "camila.vargas@gmail.com"));
        clienteRepository.save(new Cliente(null, "77777777-7", "Sebastián", "Morales", "seba.morales@gmail.com"));
        clienteRepository.save(new Cliente(null, "88888888-8", "Fernanda", "Castro", "fer.castro@gmail.com"));
        clienteRepository.save(new Cliente(null, "99999999-9", "Nicolás", "Rojas", "nico.rojas@gmail.com"));
        clienteRepository.save(new Cliente(null, "10101010-1", "Javiera", "Muñoz", "javi.munoz@gmail.com"));
        clienteRepository.save(new Cliente(null, "12121212-1", "Matías", "Herrera", "matias.herrera@gmail.com"));
        clienteRepository.save(new Cliente(null, "13131313-1", "Francisca", "Núñez", "francy.nunez@gmail.com"));
        clienteRepository.save(new Cliente(null, "14141414-1", "Ignacio", "Álvarez", "nacho.alvarez@gmail.com"));
        clienteRepository.save(new Cliente(null, "15151515-1", "Catalina", "Reyes", "cata.reyes@gmail.com"));
        clienteRepository.save(new Cliente(null, "16161616-1", "Rodrigo", "Torres", "rodri.torres@gmail.com"));
        clienteRepository.save(new Cliente(null, "17171717-1", "Daniela", "Flores", "dani.flores@gmail.com"));
        clienteRepository.save(new Cliente(null, "18181818-1", "Felipe", "Ramírez", "feli.ramirez@gmail.com"));
        clienteRepository.save(new Cliente(null, "19191919-1", "Constanza", "Vega", "coni.vega@gmail.com"));


        log.info(">>> DataInitializer: Carga finalizada. {} clientes insertados correctamente.",clienteRepository.count());

    }
}

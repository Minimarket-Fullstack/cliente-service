### PARA LA BASE DE DATOS Y PRUEBAS.

```
CREATE DATABASE IF NOT EXISTS db_minimarket_cliente;
USE db_minimarket_cliente;

INSERT INTO cliente (rut, nombre, apellido, email, activo) VALUES
('12345678-9', 'Juan', 'Pérez', 'juan.perez@gmail.com', 1),
('98765432-1', 'María', 'González', 'maria.gonzalez@gmail.com', 1),
('11111111-1', 'Carlos', 'Rodríguez', 'carlos.rodriguez@gmail.com', 1),
('22222222-2', 'Ana', 'Martínez', 'ana.martinez@gmail.com', 1),
('33333333-3', 'Pedro', 'López', 'pedro.lopez@gmail.com', 0),
('44444444-4', 'Valentina', 'Soto', 'vale.soto@gmail.com', 1),
('55555555-5', 'Diego', 'Fuentes', 'diego.fuentes@gmail.com', 1),
('66666666-6', 'Camila', 'Vargas', 'camila.vargas@gmail.com', 1),
('77777777-7', 'Sebastián', 'Morales', 'seba.morales@gmail.com', 0),
('88888888-8', 'Fernanda', 'Castro', 'fer.castro@gmail.com', 1),
('99999999-9', 'Nicolás', 'Rojas', 'nico.rojas@gmail.com', 1),
('10101010-1', 'Javiera', 'Muñoz', 'javi.munoz@gmail.com', 1),
('12121212-1', 'Matías', 'Herrera', 'matias.herrera@gmail.com', 0),
('13131313-1', 'Francisca', 'Núñez', 'francy.nunez@gmail.com', 1),
('14141414-1', 'Ignacio', 'Álvarez', 'nacho.alvarez@gmail.com', 1),
('15151515-1', 'Catalina', 'Reyes', 'cata.reyes@gmail.com', 1),
('16161616-1', 'Rodrigo', 'Torres', 'rodri.torres@gmail.com', 0),
('17171717-1', 'Daniela', 'Flores', 'dani.flores@gmail.com', 1),
('18181818-1', 'Felipe', 'Ramírez', 'feli.ramirez@gmail.com', 1),
('19191919-1', 'Constanza', 'Vega', 'coni.vega@gmail.com', 1);
```

# Pruebas Postman - cliente-service

## GET - Listar clientes activos
`GET http://localhost:8080/api/v1/clientes`

## GET - Buscar por ID
`GET http://localhost:8080/api/v1/clientes/1/existe`
`GET http://localhost:8080/api/v1/clientes/5/existe`

## GET - Buscar por RUT
`GET http://localhost:8080/api/v1/clientes/rut/12345678-9`
`GET http://localhost:8080/api/v1/clientes/rut/33333333-3`

## POST - Crear cliente
`POST http://localhost:8080/api/v1/clientes`
```json
{
    "rut": "20202020-2",
    "nombre": "Prueba",
    "apellido": "Apellido",
    "email": "prueba@gmail.com",
    "activo": true
}
```

## POST - RUT duplicado
`POST http://localhost:8080/api/v1/clientes`
```json
{
    "rut": "12345678-9",
    "nombre": "Test",
    "apellido": "Test",
    "email": "test2@gmail.com",
    "activo": true
}
```

## POST - Campos vacíos
`POST http://localhost:8080/api/v1/clientes`
```json
{
    "rut": "",
    "nombre": "",
    "apellido": "",
    "email": "noesunemail",
    "activo": true
}
```

## PUT - Actualizar cliente
`PUT http://localhost:8080/api/v1/clientes/1`
```json
{
    "nombre": "JuanActualizado",
    "apellido": "PérezActualizado",
    "email": "juan.actualizado@gmail.com",
    "activo": true
}
```

## DELETE - Desactivar cliente
`DELETE http://localhost:8080/api/v1/clientes/eliminar/2/98765432-1`

## DELETE - RUT incorrecto
`DELETE http://localhost:8080/api/v1/clientes/eliminar/2/99999999-9`

# Pruebas Postman - cliente-service

## GET - Listar clientes

`GET http://localhost:8081/api/clientes`

---

## GET - Buscar por ID

`GET http://localhost:8081/api/clientes/1`

`GET http://localhost:8081/api/clientes/5`

---

## GET - Buscar por RUT

`GET http://localhost:8081/api/clientes/rut/12345678-9`

`GET http://localhost:8081/api/clientes/rut/33333333-3`

---

## GET - Buscar por Nombre

`GET http://localhost:8081/api/clientes/nombre/Juan`

---

## POST - Crear cliente

`POST http://localhost:8081/api/clientes`

```json
{
    "rut": "20202020-2",
    "nombre": "Prueba",
    "apellido": "Apellido",
    "email": "prueba@gmail.com"
}
```

---

## POST - RUT duplicado

`POST http://localhost:8081/api/clientes`

```json
{
    "rut": "12345678-9",
    "nombre": "Test",
    "apellido": "Test",
    "email": "test2@gmail.com"
}
```

---

## POST - Campos vacíos (validación)

`POST http://localhost:8081/api/clientes`

```json
{
    "rut": "",
    "nombre": "",
    "apellido": "",
    "email": "noesunemail"
}
```

---

## PUT - Actualizar cliente

`PUT http://localhost:8081/api/clientes/1`

```json
{
    "rut": "12345678-9",
    "nombre": "JuanActualizado",
    "apellido": "PérezActualizado",
    "email": "juan.actualizado@gmail.com"
}
```

---

## DELETE - Eliminar cliente

`DELETE http://localhost:8081/api/clientes/2`

---

> Nota
> ```properties
> server.port=8081
> ```

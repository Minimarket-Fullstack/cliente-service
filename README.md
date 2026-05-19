# Pruebas Postman - cliente-service

> **Autenticación requerida** en todos los endpoints excepto `/auth/login`.
> Agregar en cada request: `Authorization: Bearer <token>`

---

## POST - Login (obtener token)

`POST http://localhost:8081/auth/login`

```json
{
    "username": "admin",
    "password": "password1234"
}
```

---

## POST - Login usuario USER

`POST http://localhost:8081/auth/login`

```json
{
    "username": "user",
    "password": "password12345"
}
```

---

## GET - Listar clientes

`GET http://localhost:8081/api/clientes`

---

## GET - Buscar por ID

`GET http://localhost:8081/api/clientes/1`

`GET http://localhost:8081/api/clientes/5`

---

## GET - Buscar por ID inexistente (404)

`GET http://localhost:8081/api/clientes/999`

---

## GET - Buscar por RUT

`GET http://localhost:8081/api/clientes/rut/12345678-9`

`GET http://localhost:8081/api/clientes/rut/33333333-3`

---

## GET - Buscar por Nombre

`GET http://localhost:8081/api/clientes/nombre/Juan`

`GET http://localhost:8081/api/clientes/nombre/Maria`

---

## GET - Buscar nombre inexistente (204)

`GET http://localhost:8081/api/clientes/nombre/Zzzzzz`

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

## POST - RUT duplicado (409)

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

## POST - Campos vacíos (validación 400)

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
    "nombre": "JuanActualizado",
    "apellido": "PérezActualizado",
    "email": "juan.actualizado@gmail.com"
}
```

---

## PUT - Actualizar cliente inexistente (404)

`PUT http://localhost:8081/api/clientes/999`

```json
{
    "nombre": "NoExiste",
    "apellido": "NoExiste",
    "email": "noexiste@gmail.com"
}
```

---

## PUT - Actualizar con email inválido (400)

`PUT http://localhost:8081/api/clientes/1`

```json
{
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "noesunemail"
}
```

---

## DELETE - Eliminar cliente

`DELETE http://localhost:8081/api/clientes/2`

---

## DELETE - Eliminar cliente ya eliminado (409)

`DELETE http://localhost:8081/api/clientes/2`

---

## DELETE - Eliminar cliente inexistente (404)

`DELETE http://localhost:8081/api/clientes/999`

---

## GET - Verificar borrado lógico (404)

Después del DELETE, confirmar que el cliente ya no aparece:

`GET http://localhost:8081/api/clientes/2`

---

## GET - Sin token (401)

`GET http://localhost:8081/api/clientes`

Sin header `Authorization`.

---

## POST - USER intenta crear cliente (403)

`POST http://localhost:8081/api/clientes`

Con token del usuario `user` (rol USER, no puede crear):

```json
{
  "rut": "21212121-2",
  "nombre": "NoPuede",
  "apellido": "Crear",
  "email": "nopuede@gmail.com"
}
```
---

## GET - Buscar por RUT de cliente eliminado (404)

Verificar que el borrado lógico también aplica al buscar por RUT.
El cliente `33333333-3` existe en la BD pero tiene `activo = false`:

`GET http://localhost:8081/api/clientes/rut/33333333-3`

Debe retornar `404` y no exponer el registro eliminado.
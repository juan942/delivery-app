# Delivery API

El siguiente proyecto se creo como parte de la solución del examen tecnico para la empresa AIT.

La API cubre las acciones básicas de gestion de órdenes de transporte: crear órdenes, cambiar de estado, registrar conductores y asignarlos a una orden.

## Requisitos

- Docker

## Cómo levantar el proyecto

1. Nos colocaremos en la raíz del proyecto, en este punto podremos ver un archivo `.env.example`

2. Renombrar dicho archivo quitando la segunda extensión `.env.example -> .env`

3. Desde la raíz del repositorio entramos a la carpeta de docker:
```bash
cd docker
```

4. Ejecutamos el siguiente comando para levantar la aplicación
```bash
docker-compose up --build -d
```

Una vez la aplicación este arriba, podremos consultar los siguientes recursos.

| Recurso | URL |
|---|---|
| Documentación Swagger | http://localhost:8080/swagger-ui/index.html |
| Estado de la app | http://localhost:8080/actuator/health |

## Cómo entrar

Todos los endpoints piden un token de seguridad, menos el login.
El usuario que viene creado es:

- **Usuario:** `admin`
- **Contraseña:** `admin123`

La respuesta trae un `token` que dura 15 minutos.

Lo más cómodo es probar desde Swagger: ejecutando el endpoint `/auth/login`, copia el token y colocalo en el apartado de seguridad.

## Endpoints

### Autenticación

| Método | Ruta | Qué hace |
|---|---|---|
| POST | `/auth/login` | Devuelve el token JWT |

### Órdenes

| Método | Ruta | Qué hace |
|---|---|---|
| POST | `/orders` | Crea una orden (nace en estado `CREATED`) |
| GET | `/orders/{id}` | Consulta una orden por su ID |
| PATCH | `/orders/{id}/status` | Cambia el estado de la orden |
| GET | `/orders` | Lista las órdenes, con filtros opcionales |

Filtros de `GET /orders` (todos opcionales y combinables):

`status`: `CREATED`, `IN_TRANSIT`, `DELIVERED` o `CANCELLED`

`origin` y `destination`: texto que contenga el origen o el destino

`createdFrom` y `createdTo`: rango de fechas de creación, en formato `YYYY-MM-dd`

Cambios de estado permitidos:

- `CREATED` → `IN_TRANSIT` o `CANCELLED`
- `IN_TRANSIT` → `DELIVERED`
- `DELIVERED` y `CANCELLED` son estados finales

### Conductores

| Método | Ruta | Qué hace |
|---|---|---|
| POST | `/drivers` | Crea un conductor |
| GET | `/drivers/active` | Lista los conductores activos |

### Asignaciones

| Método | Ruta | Qué hace |
|---|---|---|
| POST | `/assignments/assign/{orderId}/{driverId}` | Asigna un conductor a una orden |
| GET | `/assignments` | Lista las asignaciones , con filtros opcionales |

Para asignar, la orden tiene que estar en `CREATED` y el conductor activo:

Filtros de `GET /assignments` (todos opcionales y combinables):

`orderId`: ID de orden asignada

`driverId`: ID del conductor asignado

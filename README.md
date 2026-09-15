# SINFO

Aplicación móvil Android para la gestión de alumnos, desarrollada como parte de la Tarea 2 del curso de Software Engineering with AI (SENATI). Consume el webservice **WS-SENATI**.

## Descripción técnica

SINFO permite registrar, listar, buscar, actualizar y eliminar alumnos, consumiendo un webservice REST (WS-SENATI) mediante peticiones HTTP. El flujo de la app está pensado para incluir una pantalla de bienvenida (Splash), inicio de sesión (Login), un menú principal, y las pantallas del CRUD.

### Flujo de navegación

```
Splash (1-3 seg) → Login → Menú → Listar / Buscar / Registrar
```

Desde el **Menú**, el usuario accede a:
- **Listar** — lista simple de alumnos (`Listado`) y una versión con `RecyclerView` (`ListaDetallada`)
- **Buscar** — buscar un alumno por ID, con opción de actualizar o eliminar (`Buscador`)
- **Registrar** — formulario para dar de alta un nuevo alumno (`Registro`)

## Tecnologías utilizadas

- **Java** (Android Studio)
- **Volley** — librería para consumir el webservice (peticiones HTTP asíncronas)
- **RecyclerView** — para el listado detallado de alumnos
- **JSON** (org.json) — parseo de las respuestas del webservice

## Arquitectura del proyecto

| Componente | Rol |
|---|---|
| `MainActivity` | Punto de entrada de la app |
| `Menu` | Pantalla principal con acceso a Listar / Buscar / Registrar |
| `Registro` | Formulario de alta — envía un `POST` al webservice |
| `Listado` | Lista simple de alumnos (`ListView`) — consume `GET /alumnos` |
| `ListaDetallada` | Lista con `RecyclerView`, muestra apellidos/nombres, dirección y teléfono por alumno |
| `AdapterDatos` | Adapter del `RecyclerView`, enlaza cada `Alumno` con su vista (`item_list.xml`) |
| `Buscador` | Busca un alumno por ID (`GET /alumnos/:id`), permite actualizar (`PUT`) o eliminar (`DELETE`) |
| `Alumno` | Clase modelo — representa un alumno con sus atributos (apellidos, nombres, teléfono, dirección, email) |

### Comunicación con el webservice

Todas las pantallas que consumen el WS usan una `RequestQueue` de Volley y apuntan al endpoint base:
```
http://<IP_LOCAL>:3000/alumnos
```
Cada respuesta del webservice se procesa de forma asíncrona (`Response.Listener` / `Response.ErrorListener`), mostrando mensajes al usuario mediante `Toast` o rellenando los campos del formulario según corresponda.

### Validación

- `Buscador` valida que el campo de ID no esté vacío antes de realizar la búsqueda, y muestra un diálogo de confirmación antes de actualizar o eliminar un registro.
- `Registro` (pendiente de reforzar) valida los campos obligatorios antes de enviar el formulario al webservice.

## Estado actual

- [x] CRUD funcional (Registro, Listado, Buscador, ListaDetallada con RecyclerView)
- [x] Consumo del webservice mediante Volley
- [x] Manejo de errores del servidor (por ejemplo, 404 en Buscador)
- [x] Pantalla de Login
- [x] Splash screen (Activity, 1-3 seg)
- [x] Navegación completa desde el Menú (conectar botones a cada pantalla)
- [x] Ícono personalizado de la app
- [x] Validación de campos vacíos en Registro

## Requisitos para ejecutar

- Android Studio
- El webservice **WS-SENATI** debe estar corriendo y accesible desde la red local (ver IP configurada en cada Activity que consume la API)

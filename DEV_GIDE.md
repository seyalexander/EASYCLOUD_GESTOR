# 🛠 Guía de Desarrollo - SeyaCloud Gestión Sistema

Este documento contiene comandos útiles para desarrollo, verificación de servicios y mantenimiento del entorno local.

---

# 📦 Servicios utilizados

* Redis (Cache)
* SQL Server (Base de datos)
* Spring Boot (Backend API)

---

# 🔴 Redis - Verificación y mantenimiento

## Conectarse a Redis CLI

```bash
redis-cli
```

Si Redis está activo verás algo como:

```text
127.0.0.1:6379>
```

---

## Ver todas las claves almacenadas

```bash
keys *
```

Ejemplo de salida:

```text
1) "familias::1"
2) "productos::all"
```

---

## Ver contenido de una clave

```bash
get nombreClave
```

Ejemplo:

```bash
get familias::1
```

---

## Eliminar una clave específica

```bash
del nombreClave
```

Ejemplo:

```bash
del familias::1
```

---

## Limpiar toda la cache de Redis

⚠ **Advertencia:** elimina todas las claves almacenadas.

```bash
flushall
```

---

## Limpiar solo la base de datos actual

```bash
flushdb
```

---

## Levantar Redis manualmente

Si Redis no está corriendo:

```bash
redis-server
```

---

## Verificar que Redis está corriendo en Windows

```bash
tasklist | findstr redis
```

Ejemplo de salida:

```text
redis-server.exe              4636 Services
```

---

# 🧪 Prueba rápida de Redis

Guardar un valor:

```bash
set test "hola"
```

Leer valor:

```bash
get test
```

Resultado esperado:

```text
"hola"
```


# 🧹 Limpieza de cache en desarrollo

Cuando cambies datos directamente en la base de datos o notes que la API devuelve datos antiguos:

1️⃣ Entrar a Redis CLI

```bash
redis-cli
```

2️⃣ Ver claves almacenadas

```bash
keys *
```

3️⃣ Limpiar cache

```bash
flushall
```

---

# ⚙ Uso de Redis en Spring Boot

La lista de familias se cachea usando:

```java
@Cacheable(value = "familias")
```

Cuando se edita, activa o anula una familia se debe limpiar la cache:

```java
@CacheEvict(value = "familias", allEntries = true)
```

Esto asegura que la próxima consulta obtenga datos actualizados desde la base de datos.

---

# 📑 Acceso a Swagger

Documentación automática disponible en:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# 👨‍💻 Notas para desarrolladores

* Redis se usa para **optimizar consultas frecuentes**.
* No almacenar información crítica únicamente en cache.
* Cada operación de **crear, editar o eliminar datos** debe limpiar la cache correspondiente.
* Usar `@CacheEvict` para mantener consistencia.

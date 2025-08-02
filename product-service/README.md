# Product Service

Este proyecto es un microservicio desarrollado en Java con Spring Boot, encargado de la gestión de productos dentro de un sistema de inventario. Forma parte de una arquitectura de microservicios y expone una API REST para interactuar con los productos.

## ¿Qué hace este proyecto?
Permite realizar operaciones básicas sobre productos, como:
- Crear nuevos productos
- Consultar la información de un producto por su identificador
- Listar todos los productos registrados

## Endpoints disponibles

La API expone los siguientes endpoints bajo el prefijo `/api/v1/products`:

### 1. Crear producto
- **POST** `/api/v1/products`
- **Descripción:** Crea un nuevo producto en el sistema.
- **Body:** JSON con los datos del producto (según el DTO de creación).
- **Respuesta:** El producto creado.

### 2. Consultar producto por ID
- **GET** `/api/v1/products/{id}`
- **Descripción:** Obtiene la información de un producto específico por su identificador.
- **Respuesta:** Los datos del producto.

### 3. Listar todos los productos
- **GET** `/api/v1/products`
- **Descripción:** Devuelve una lista con todos los productos registrados.
- **Respuesta:** Array de productos.

## Requisitos
- Java 17+
- Maven

## Ejecución
1. Clona el repositorio.
2. Ejecuta `./mvnw spring-boot:run` para iniciar el microservicio.

---

Para más detalles sobre la configuración y uso, revisa la documentación interna o los archivos de configuración en `src/main/resources`.


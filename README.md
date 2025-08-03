# Product Inventory Microservices

## 📋 Descripción

Este proyecto implementa una arquitectura de microservicios desarrollada en **Java 21** y **Spring Boot 3.5.4** para la gestión de productos e inventario. El sistema está compuesto por dos microservicios independientes que trabajan en conjunto para proporcionar una solución completa de gestión de productos.

## 📚 Tabla de Contenido

### 📖 Documentación Principal
- [🛠️ Guía de Implementación](./IMPLEMENTATION_GUIDE.md) - Instalación, configuración y uso del sistema

### 🏗️ Arquitectura y Diseño
- [🏗️ Arquitectura Hexagonal](./HEXAGONAL_ARCHITECTURE.md) - Documentación de la arquitectura hexagonal implementada
- [🚀 Propuesta de Escalabilidad](./SCALABILITY_PROPOSAL.md) - Estrategias de escalabilidad futura

### 🔄 Procesos y Estrategias
- [🚀 Git Flow Strategy](./GIT_FLOW_STRATEGY.md) - Estrategia de versionamiento y flujo de trabajo

## 🏗️ Arquitectura

### Microservicios

1. **Product Service** (`product-service`)
   - **Puerto**: 8080
   - **Responsabilidad**: Gestión de productos (CRUD)
   - **Base de datos**: PostgreSQL (schema: `product_schema`)

2. **Inventory Service** (`inventory-service`)
   - **Puerto**: 8081
   - **Responsabilidad**: Gestión de inventario y compras
   - **Base de datos**: PostgreSQL (schema: `inventory_schema`)
   - **Comunicación**: Se comunica con Product Service para obtener información de productos

### Diagrama de Arquitectura

![Arquitectura del Sistema](./resources/images/Arquitectura.jpg)

## 🗄️ Diseño Entidad-Relación

### Diagrama Entidad-Relación

![Diagrama Entidad-Relación](./resources/images/entidad_relacion_db.jpg)

## 🚀 Tecnologías Utilizadas

- **Java**: 21
- **Spring Boot**: 3.5.4
- **Spring Data JPA**: Para persistencia de datos
- **PostgreSQL**: Base de datos principal
- **H2**: Base de datos para pruebas
- **MapStruct**: Para mapeo de objetos
- **Lombok**: Para reducir código boilerplate
- **Spring Security**: Para autenticación por API Key
- **Spring Actuator**: Para monitoreo

## 📦 Estructura del Proyecto

```
product-inventory-microservices/
├── product-service/          # Microservicio de productos
├── inventory-service/        # Microservicio de inventario
├── resources/               # Recursos del proyecto
│   ├── docker/             # Configuración Docker
│   ├── postman/            # Colecciones de Postman
│   └── images/             # Imágenes de documentación
└── documentation/           # Documentación adicional
```

## 🔗 Enlaces Rápidos

- **📖 Guía de Implementación**: [Ver documentación completa](./IMPLEMENTATION_GUIDE.md)
- **🏗️ Arquitectura Hexagonal**: [Ver detalles de arquitectura](./HEXAGONAL_ARCHITECTURE.md)
- **🚀 Escalabilidad**: [Ver propuesta de escalabilidad](./SCALABILITY_PROPOSAL.md)
- **🔄 Git Flow**: [Ver estrategia de versionamiento](./GIT_FLOW_STRATEGY.md)


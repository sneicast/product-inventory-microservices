-- Crear esquemas para los microservicios
CREATE SCHEMA IF NOT EXISTS product_schema;
CREATE SCHEMA IF NOT EXISTS inventory_schema;

-- Asignar permisos al usuario dev_user
GRANT ALL PRIVILEGES ON SCHEMA product_schema TO dev_user;
GRANT ALL PRIVILEGES ON SCHEMA inventory_schema TO dev_user;

-- Crear extensiones necesarias
CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; 
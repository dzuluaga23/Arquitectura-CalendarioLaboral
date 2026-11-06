# API Calendario Laboral

API RESTful desarrollada en **Java** con **Spring Boot 3** bajo una **Arquitectura Cebolla (Onion Architecture)** multi-módulo, diseñada para la gestión de días festivos y tipos de días en un calendario laboral, integrándose con una API externa de festivos.

---

## Arquitectura del Proyecto

El proyecto está dividido en 5 submódulos de Maven para garantizar la separación de responsabilidades:

- **`dominio`**: Contiene el modelo de dominio core (entidades puras sin dependencias del marco de trabajo).
- **`core`**: Define las interfaces de los servicios y repositorios (contratos del sistema).
- **`aplicacion`**: Implementa los casos de uso y lógica de negocio principales.
- **`infraestructura`**: Maneja la persistencia de datos (JPA / Hibernate / PostgreSQL), el cliente HTTP (`RestClient`) para la API externa y los DTOs/mapeadores.
- **`presentacion`**: Expone los controladores REST y la configuración ejecutable de Spring Boot.

---

## Configuración e Instalación

### 1. Clonar el Repositorio
```bash
git clone https://github.com/dzuluaga23/Arquitectura-CalendarioLaboral.git
cd Arquitectura-CalendarioLaboral
```

---

### 2. Configurar application.properties
El proyecto utiliza una configuración directa en presentacion/src/main/resources/application.properties:
```bash
server.port=8080

# Conexión a PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/calendario_db
spring.datasource.username=postgres
spring.datasource.password=admin
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración JPA / Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# URL Base de la API Festivos externa
api.festivos.url=http://localhost:3000/festivos
```

---

### 3. Compilar e Instalar Módulos
Ejecuta el siguiente comando en la raíz del proyecto para compilar los módulos:
```bash
mvn clean install -DskipTests
```

---

### 4. Ejecución de la API
Para levantar el servidor de desarrollo, ejecuta el módulo de presentación:
```bash
mvn spring-boot:run -pl presentacion
```
La aplicación se iniciará por defecto en el puerto 8080.

---

### 5. Endpoints
Una vez iniciada la aplicación, accede a Swagger UI en:

URL: http://localhost:8080/swagger-ui/index.html

### Endpoints Principales

#### Tipos de Día (`/api/tipos`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **`GET`** | `/api/tipos/listar` | Obtiene la lista completa de tipos de días. |
| **`GET`** | `/api/tipos/obtener/{id}` | Consulta la información de un tipo de día por su ID. |

Calendario Laboral (`/api/calendario`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **`GET`** | `/api/calendario/listar` | Obtiene todos los registros locales del calendario. |
| **`GET`** | `/api/calendario/es-festivo/{anio}/{mes}/{dia}` | Consulta a la API externa si una fecha específica es festiva. |
| **`GET`** | `/api/calendario/festivos-año/{anio}` | Obtiene la lista de festivos del año consultando a la API externa. |

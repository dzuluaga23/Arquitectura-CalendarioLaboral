# API Calendario Laboral

API RESTful desarrollada en **Java** con **Spring Boot 3** bajo una **Arquitectura Cebolla (Onion Architecture)** multi-módulo, diseñada para la gestión de días festivos y tipos de días en un calendario laboral, integrándose con una API externa de festivos.

---

## Arquitectura del Proyecto

El proyecto está dividido en 5 submódulos de Maven para garantizar la separación de responsabilidades:

- **`dominio`**: Contiene el modelo de dominio core (entidades puras sin dependencias del marco de trabajo), DTOs y excepciones de negocio.
- **`core`**: Define las interfaces de los servicios y repositorios (contratos del sistema).
- **`aplicacion`**: Implementa los casos de uso y lógica de negocio principales.
- **`infraestructura`**: Maneja la persistencia de datos (JPA / Hibernate / PostgreSQL), el cliente HTTP (`RestClient`) para la API externa y los DTOs/mapeadores.
- **`presentacion`**: Expone los controladores REST, los handlers de excepciones y la configuración ejecutable de Spring Boot.

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
```properties
server.port=8000

# Conexión a PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/calendariolaboral
spring.datasource.username=postgres
spring.datasource.password=<TU_CONTRASEÑA>

# Configuración JPA / Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# URL Base de la API Festivos externa (Node.js en puerto 3000)
api.festivos.url=http://localhost:3000/api
```

---

### 3. Crear la Base de Datos en PostgreSQL

Ejecutar en pgAdmin (Query Tool conectado a la BD `postgres`):

```sql
--Ejecutar primero
DROP DATABASE calendariolaboral WITH (FORCE);
--Ejecutar segundo
CREATE DATABASE calendariolaboral;
```

Para las siguientes instrucciones, se debe cambiar la conexión en pgAdmin a la BD `calendariolaboral`:

```sql
--Crear la tabla TIPO
CREATE TABLE Tipo(
	Id SERIAL PRIMARY KEY,
	Tipo VARCHAR(100) NOT NULL
);

CREATE UNIQUE INDEX ixTipo ON Tipo(Tipo);

--Crear la tabla CALENDARIO
CREATE TABLE Calendario(
	Id SERIAL PRIMARY KEY,
	Fecha DATE NOT NULL,
	IdTipo INT NOT NULL,
    CONSTRAINT fkCalendario_Tipo FOREIGN KEY (IdTipo) REFERENCES Tipo(Id),
    Descripcion VARCHAR(100) NULL
);

CREATE UNIQUE INDEX ixCalendario ON Calendario(Fecha);

--Registros tabla TIPO (datos maestros que consume la API)
INSERT INTO Tipo(Id, Tipo) VALUES(1, 'Día laboral');
INSERT INTO Tipo(Id, Tipo) VALUES(2, 'Fin de Semana ');
INSERT INTO Tipo(Id, Tipo) VALUES(3, 'Día festivo ');
```

⚠️ **Importante — alinear la secuencia de `tipo`:** como los INSERT anteriores usan `Id` explícito, la secuencia de la columna no se entera. Si algún insert a la tabla `tipo` intentara usar el id 1, fallaría con "llave duplicada". Ejecutar una sola vez al final:

```sql
SELECT setval(pg_get_serial_sequence('tipo', 'id'), (SELECT MAX(id) FROM tipo));
```
*(en minúsculas: los identificadores sin comillas se guardan en minúsculas y el segundo parámetro de la función es sensible a mayúsculas).*

---

### 4. Compilar e Instalar Módulos
Ejecuta el siguiente comando en la raíz del proyecto (requiere **Maven** y **JDK 17+**):
```bash
mvn clean install -DskipTests
```

---

### 5. Ejecución de la API
Con Maven:
```bash
mvn spring-boot:run -pl presentacion
```
O con el jar ya compilado:
```bash
java -jar presentacion\target\presentacion-0.0.1-SNAPSHOT.jar
```
La aplicación se iniciará en el puerto configurado (`server.port`). Si el puerto 8080 está ocupado por otro proceso, se puede cambiar el puerto temporalmente con `--server.port=8000` al ejecutar el jar.

---

## Integración con la API Festivos (Node.js)

La API Calendario Laboral **no calcula festivos propios**: consume la API Festivos (Node.js/Express, puerto 3000) a través de `GET /api/festivos/obtener/{anio}` (una sola llamada por año). Con cada consulta:

1. Obtiene la lista de festivos del año ya calculados por la API externa.
2. Los **persiste en PostgreSQL** (tabla `calendario`, evitando duplicados por fecha) asociándolos al tipo **"Día festivo"**.
3. Responde con la lista, y la verificación `es-festivo` se hace en memoria contra esa lista.

Por eso la API Festivos debe estar en ejecución antes de consultar los endpoints de calendario.

---

### 6. Endpoints
Una vez iniciada la aplicación, accede a Swagger UI en:

URL: http://localhost:8000/swagger-ui/index.html

### Endpoints Principales

#### Tipos de Día (`/api/tipos`) — CRUD completo
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **`GET`** | `/api/tipos/listar` | Obtiene la lista completa de tipos de días. |
| **`GET`** | `/api/tipos/obtener/{id}` | Consulta la información de un tipo de día por su ID. |
| **`POST`** | `/api/tipos/agregar` | Crea un tipo. Body: `{ "tipo": "Día especial" }`. |
| **`PUT`** | `/api/tipos/modificar` | Modifica un tipo existente. Body: `{ "id": 4, "tipo": "Nuevo nombre" }`. |
| **`DELETE`** | `/api/tipos/eliminar/{id}` | Elimina un tipo (rechazado si tiene calendarios asociados). |

#### Calendario Laboral (`/api/calendario`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **`GET`** | `/api/calendario/listar` | Obtiene todos los registros locales del calendario. |
| **`GET`** | `/api/calendario/obtener/{id}` | Consulta un registro del calendario por su ID. |
| **`GET`** | `/api/calendario/es-festivo/{anio}/{mes}/{dia}` | Indica si una fecha específica es festiva (verificación en memoria con la lista anual de la API externa). |
| **`GET`** | `/api/calendario/festivos-año/{anio}` | Obtiene la lista de festivos del año consultando a la API externa y los persiste en PostgreSQL. |

---

### Manejo de Errores
| Código | Situación |
| :--- | :--- |
| **`400`** | Año fuera del rango 1900–2099, fecha inválida, nombre de tipo vacío o duplicado, tipo inexistente, tipo con calendarios asociados al eliminar. |
| **`404`** | Registro no encontrado (tipo, calendario). |
| **`409`** | Conflicto de unicidad/integridad con la base de datos. |
| **`503`** | La API Festivos externa no está disponible o no respondió a tiempo. |

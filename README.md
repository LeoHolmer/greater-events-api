# PDyC2025 - Junín - Individual

Proyecto individual para la materia Programación Distribuida y Concurrente (PDyC) 2025, sede Junín.
aplicación para una comunidad de usuarios vinculados a eventos musicales mediante un web service Restful que implementa la arquitectura Rest.

## Tecnologías Utilizadas

- **Java 21** (LTS)
- **Spring Boot 3.4.5**
- **Spring Data JPA**
- **PostgreSQL** (base de datos)
- **Maven** (gestión de dependencias)

## Características


- Gestión de entidades JPA
- API RESTful
- Base de datos PostgreSQL
- Tests unitarios e integración

## Requisitos Previos

- JDK 21
- Maven 3.6+
- PostgreSQL

## Instalación y Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/LeoHolmer/pdyc2025-junin-individual.git
   cd pdyc2025-junin-individual
   ```

2. Configura la base de datos en `src/main/resources/application.properties`

3. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

## Tests

Ejecuta los tests con:
```bash
./mvnw test
```

## Estructura del Proyecto

- `src/main/java` - Código fuente
- `src/main/resources` - Recursos y configuración
- `src/test/java` - Tests

## Contribución

Proyecto académico individual.

## Licencia

Este proyecto es para fines educativos.

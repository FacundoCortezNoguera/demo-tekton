# Demo Tekton

Proyecto Spring Boot para cálculo con porcentaje usando caché en Redis, conexión a PostgreSQL y logs automáticos con persistencia.

## 🚀 Cómo levantar el proyecto

### Requisitos:
- Docker + Docker Compose
- Java 21
- Maven 3.8+

### 💻 Opción 1: Clonar, compilar y levantar con Docker Compose local

#### 1. Cloná el repositorio
```bash
git clone https://github.com/tu-usuario/demo-tekton.git

cd demo-tekton
```

#### 2. Cloná el repositorio
```bash
mvn clean package
```

#### 3. Levantá los servicios
```bash 
docker compose up --build
```

### ☁️ Opción 2: Usar la imagen publicada en Docker Hub (sin compilar nada)

#### 1. Bajá imagen publicada en Docker Hub
```bash
docker pull facundocortez/my-api:latest
```

#### 2. Corré la imagen bajada
```bash
docker run -p 8080:8080 facundocortez/my-api:latest
```

### Esto va a levantar:
- Spring Boot API en http://localhost:8080
- PostgreSQL (DB: yourdb, user: youruser, pass: yourpassword)
- Redis

## 📌Endpoints disponibles

| Metodo | Endpoint   | Descripcion 
|--------|------------|-------------------
| GET    | /calculate	 | 	Calcula suma + %|
| GET    | /logs	     | 	Retorna logs|
| GET    | /external/percentage	| 	/external/percentage|


## 🛠 Herramientas usadas
- Spring Boot 3.4.5
- PostgreSQL (via Docker)
- Redis (caché)
- JPA/Hibernate
- Lombok
- Postman
- Docker Compose

## 📝 Notas
- Los errores son manejados globalmente y se registran en base de datos.
- El porcentaje externo se cachea por 30 minutos en Redis.
- Si el servicio externo falla, se usa el valor cacheado; si no hay, se lanza error.

## 🤝 Autor
Facundo Cortez Noguera
# Explore California JPA Service

This project is a Spring Boot microservice that stores data in MySQL. It is simplified to a single JPA service and includes Docker Compose for local testing.

## Prerequisites

- Java 17+
- Docker and Docker Compose

## Build and Test

Run the unit tests and build the Docker image using the Maven wrapper:

```bash
./mvnw test
./mvnw -DskipTests compile jib:dockerBuild
```

The `jib:dockerBuild` goal creates a local image tagged `explorecali-jpa:3.0.0`.

## Run with Docker Compose

Start the application and MySQL database:

```bash
docker compose up
```

Docker Compose launches a `mysql-db` service and a `jpa-app` service.

## Exercise the API

After both containers are up, interact with the REST endpoints:

```bash
# Create a tour package
curl -X POST http://localhost:8080/packages \
  -H 'Content-Type: application/json' \
  -d '{"code":"BB","name":"Beekeepers you Betcha"}'

# List tour packages
curl http://localhost:8080/packages

# Delete the tour package
curl -X DELETE http://localhost:8080/packages/BB
```

Use `docker compose down` to stop the services when finished.

## License

This project is licensed under the [MIT License](LICENSE).

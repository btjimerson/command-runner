# Technology Stack

## Core Technologies
- Java 17
- Spring Boot 3.4.x
- Maven
- Thymeleaf (templating engine)
- Docker

## Key Dependencies
- Spring Boot Web
- Spring Boot Actuator
- Apache Commons Text
- Fabric8 Kubernetes Client (for K8s integration)
- Spring Boot DevTools (development only)

## Build System
The project uses Maven as its build system with the standard Spring Boot Maven plugin.

## Common Commands

### Building the Application
```bash
mvn clean package
```

### Running Locally
```bash
mvn spring-boot:run
```

### Building Docker Image
The application includes a Dockerfile for containerization. The Docker image includes:
- Eclipse Temurin JDK 19
- Network diagnostic tools (dig, ping, traceroute, etc.)
- kubectl CLI

### Load Testing
A load generation script is available:
```bash
./bin/load-generator.sh http://localhost:8080 100
```
Where the first argument is the base URL and the second is the number of loops.

### API Usage
```bash
curl --location --request POST "http://localhost:8080/api/v1/command" \
  -H "Content-Type: application/json" \
  --data-raw '{"command": "dig www.google.com"}'
```

## Deployment
The application is designed to be deployed in Kubernetes using the provided manifests:
- `manifests/command-runner.yaml`: Main deployment manifest
- `manifests/load-balancer.yaml`: LoadBalancer service for external access
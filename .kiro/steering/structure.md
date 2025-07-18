# Project Structure

## Overview
The Command Runner project follows standard Spring Boot application structure with Maven conventions.

## Key Directories and Files

### Source Code
- `src/main/java/dev/snbv2/command/`: Core application code
  - `CommandRunnerApplication.java`: Main Spring Boot application entry point
  - `CommandRunnerController.java`: Web UI controller
  - `CommandRunnerAPIController.java`: REST API controller
  - `Command.java`: Command model class
  - `CommandHistory.java`: Command history tracking

### Resources
- `src/main/resources/`: Application resources
  - `application.properties`: Spring Boot configuration
  - `templates/`: Thymeleaf templates
    - `index.html`: Main web interface
  - `static/`: Static resources (CSS, JS, images)

### Tests
- `src/test/java/dev/snbv2/command/`: Test code
  - `CommandRunnerApplicationTests.java`: Application tests

### Deployment
- `Dockerfile`: Container definition
- `manifests/`: Kubernetes deployment manifests
  - `command-runner.yaml`: Main deployment
  - `load-balancer.yaml`: LoadBalancer service

### Build
- `pom.xml`: Maven project definition
- `mvnw` and `mvnw.cmd`: Maven wrapper scripts

### Utilities
- `bin/`: Utility scripts
  - Contains load generation scripts

## Code Organization Patterns
- Controllers are separated by responsibility (Web UI vs API)
- Model classes are simple POJOs
- Spring Boot conventions are followed throughout
- Kubernetes integration is handled via the Fabric8 client
- Command execution uses Java's Runtime.exec() mechanism
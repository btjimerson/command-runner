
# Command runner

This is a Spring Boot application that is meant to let you run shell commands through a web interface.  It's primary purpose is to replace a `kubectl exec` command.

## Running in a Kubernetes cluster

This application is designed to be run as a pod in Kubernetes, to allow for command execution within a cluster.

You can deploy the application with this [manifest](./manifests/command-runner.yaml).

The web interface will be available on port 8080. You can either port forward 8080 to your machine, or create an ingress for the application. There is a simple `LoadBalancer` service in this [manifest](./manifests/load-balancer.yaml).

## Load generator

There is a load generation script in this repository that can be handy to generate traffic while demonstrating this such as egress to telemetry generation. To run the script:

```bash
./bin/load-generator.sh http://localhost:8080 100
```

Where the first argument is the base URL of the application, and the second is the number of loops to run.

## Building and running locally

You can build and run this application locally with the standard Spring Boot commands:

```bash
mvn clean package
mvn spring-boot:run
```

## API

There is a simple REST API endpoint for running commands too. You can pass a single command to run in the body, like this:

```bash
curl --location --request POST "http://localhost:8080/api/v1/command" -H "Content-Type: application/json" --data-raw '{"command": "dig www.google.com"} 
```
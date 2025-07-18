# Command Runner

Command Runner is a Spring Boot application that provides a web interface and REST API for executing shell commands. It's primarily designed to replace `kubectl exec` commands in Kubernetes environments.

## Core Features

- Web interface for executing shell commands
- REST API endpoint for programmatic command execution
- Command history tracking in web sessions
- Kubernetes node information retrieval
- Designed to run as a pod within Kubernetes clusters

## Use Cases

- Executing shell commands within a Kubernetes cluster
- Debugging network connectivity issues
- Running diagnostic tools like dig, ping, traceroute
- Generating load for telemetry demonstrations
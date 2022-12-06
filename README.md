
# Command runner

This is a Spring Boot application that is meant to let you run shell commands through a web interface.  It's primary purpose is to replace a `kubectl exec` command.

## Running

This application is meant to be run as a pod in Kubernetes, to allow for command execution within a cluster.

To build the application, run `mvn clean install`.

There should be a LoadBalancer installed in the command-runner namespace for the application.

## API

There is a REST API endpoint for running commands too.  You can view the documentation [here](https://documenter.getpostman.com/view/1749839/Uyr4KfSG)

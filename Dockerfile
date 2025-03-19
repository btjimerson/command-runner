FROM maven:3.8.7-eclipse-temurin-19 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM eclipse-temurin:19-jammy
RUN apt update
RUN apt install -y net-tools dnsutils telnet iputils-ping traceroute ca-certificates curl jq
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
RUN chmod +x kubectl
RUN mv kubectl /usr/local/bin
WORKDIR /opt
EXPOSE 8080
COPY --from=build /home/app/target/*.jar /opt/app.jar
CMD ["java", "-jar", "/opt/app.jar"]


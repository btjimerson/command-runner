FROM eclipse-temurin:17
RUN apt-get update \
    && apt install -y net-tools dnsutils telnet
WORKDIR /opt
EXPOSE 8080
COPY target/*.jar /opt/app.jar
CMD java -jar /opt/app.jar

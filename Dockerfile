FROM eclipse-temurin:17
RUN apt-get update
RUN apt-get install -y net-tools dnsutils telnet iputils-ping traceroute ca-certificates curl
RUN curl -fsSLo /etc/apt/keyrings/kubernetes-archive-keyring.gpg https://packages.cloud.google.com/apt/doc/apt-key.gpg
RUN echo "deb [signed-by=/etc/apt/keyrings/kubernetes-archive-keyring.gpg] https://apt.kubernetes.io/ kubernetes-xenial main" | tee /etc/apt/sources.list.d/kubernetes.list
RUN apt-get update
RUN apt-get install -y kubectl
WORKDIR /opt
EXPOSE 8080
COPY target/*.jar /opt/app.jar
CMD java -jar /opt/app.jar

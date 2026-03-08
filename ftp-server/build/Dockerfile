FROM azul/zulu-openjdk:21
RUN apt-get update && \
    apt-get install -y \
        mc \
        lsof \
        net-tools
RUN mkdir -p /var/local/ftp-server
WORKDIR /var/local/ftp-server
COPY target/ftp-server-1.0.0-SNAPSHOT.jar ./application.jar
COPY build/users.properties /var/local/ftp-server/users.properties
EXPOSE 20
EXPOSE 21
CMD ["bash", "-c", "java -jar application.jar"]
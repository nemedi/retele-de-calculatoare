FROM azul/zulu-openjdk:21
RUN apt-get update && \
    apt-get install -y \
        xvfb \
        mc \
        lsof \
        net-tools
RUN mkdir -p /var/local/webserver
WORKDIR /var/local/webserver
COPY target/webserver-1.0.0-SNAPSHOT.jar ./application.jar
EXPOSE 6969
ENV DEBUG "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
CMD ["bash", "-c", "java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=127.0.0.1:5005 -jar application.jar"]
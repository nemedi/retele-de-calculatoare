FROM azul/zulu-openjdk:21
RUN apt-get update && \
    apt-get install -y \
        mc \
        lsof \
        net-tools
RUN mkdir -p /var/local/shell/server
WORKDIR /var/local/shell
COPY  src/server/*.java /var/local/shell/server/
COPY src/settings.properties /var/local/shell/settings.properties
RUN javac server/*.java
RUN rm /var/local/shell/server/*.java
EXPOSE 6969
CMD ["bash", "-c", "java server.Program"]
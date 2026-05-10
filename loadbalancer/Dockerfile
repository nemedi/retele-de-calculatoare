FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /transformer
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B

FROM eclipse-temurin:21-jre-jammy
WORKDIR /transformer
COPY --from=build /transformer/target/transformer-1.0.0.jar transformer.jar
ENTRYPOINT ["java", "-jar", "transformer.jar"]
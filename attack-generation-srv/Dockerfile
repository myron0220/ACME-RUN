FROM eclipse-temurin:19-alpine

RUN mkdir app
COPY ./target/attack-generation-srv-0.0.1-SNAPSHOT.jar ./app/attack-generation-srv.jar
CMD ["java", "-jar", "./app/attack-generation-srv.jar"]
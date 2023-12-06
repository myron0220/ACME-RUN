FROM eclipse-temurin:19-alpine

RUN mkdir app
COPY ./target/game-centre-srv-0.0.1-SNAPSHOT.jar ./app/game-centre-srv.jar
CMD ["java", "-jar", "/app/game-centre-srv.jar"]
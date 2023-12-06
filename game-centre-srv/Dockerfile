FROM eclipse-temurin:19-alpine

RUN mkdir /opt/app
COPY ./target/game-centre-srv-0.0.1-SNAPSHOT.jar /opt/app/game-centre-srv.jar
CMD ["java", "-jar", "/opt/app/game-centre-srv.jar"]
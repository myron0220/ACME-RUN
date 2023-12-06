FROM eclipse-temurin:19-alpine

RUN mkdir app
COPY ./target/point-srv-0.0.1-SNAPSHOT.jar ./app/point-srv.jar
CMD ["java", "-jar", "./app/point-srv.jar"]
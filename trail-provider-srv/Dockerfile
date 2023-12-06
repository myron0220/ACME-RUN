FROM eclipse-temurin:19-alpine

RUN mkdir app
COPY ./target/trail-provider-srv-0.0.1-SNAPSHOT.jar ./app/trail-provider-srv.jar
CMD ["java", "-jar", "./app/trail-provider-srv.jar"]
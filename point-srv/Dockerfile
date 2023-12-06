FROM eclipse-temurin:19-alpine

RUN mkdir /opt/app
COPY ./target/point-srv-0.0.1-SNAPSHOT.jar /opt/app/point-srv.jar
CMD ["java", "-jar", "/opt/app/point-srv.jar"]
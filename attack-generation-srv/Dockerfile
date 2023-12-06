FROM eclipse-temurin:19-alpine

RUN mkdir /opt/app
COPY ./target/attack-generation-srv-0.0.1-SNAPSHOT.jar /opt/app/attack-generation-srv.jar
CMD ["java", "-jar", "/opt/app/attack-generation-srv.jar"]
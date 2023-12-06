FROM eclipse-temurin:19-alpine

RUN mkdir /opt/app
COPY ./target/trail-provider-srv-0.0.1-SNAPSHOT.jar /opt/app/trail-provider-srv.jar
CMD ["java", "-jar", "/opt/app/trail-provider-srv.jar"]
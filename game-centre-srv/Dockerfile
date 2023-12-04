FROM eclipse-temurin:19-alpine

RUN mkdir /opt/app
COPY ./target/biometric-srv-0.0.1-SNAPSHOT.jar /opt/app/biometric-srv.jar
CMD ["java", "-jar", "/opt/app/biometric-srv.jar"]
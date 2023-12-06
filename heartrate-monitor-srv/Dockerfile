FROM eclipse-temurin:19-alpine

RUN mkdir app
COPY ./target/heartrate-monitor-srv-0.0.1-SNAPSHOT.jar ./app/heartrate-monitor-srv.jar
CMD ["java", "-jar", "/app/heartrate-monitor-srv.jar"]
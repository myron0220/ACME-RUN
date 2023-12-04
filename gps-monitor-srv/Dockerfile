FROM eclipse-temurin:19-alpine

RUN mkdir /opt/app
COPY ./target/heartrate-monitor-srv-0.0.1-SNAPSHOT.jar /opt/app/heartrate-monitor-srv.jar
CMD ["java", "-jar", "/opt/app/heartrate-monitor-srv.jar"]
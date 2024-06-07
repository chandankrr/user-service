FROM amazoncorretto:21
LABEL maintainer="chandankrr"
WORKDIR /app
COPY target/user-service-0.0.1-SNAPSHOT.jar /app/user-service-0.0.1-SNAPSHOT.jar
EXPOSE 9820
ENTRYPOINT ["java", "-jar", "/app/user-service-0.0.1-SNAPSHOT.jar"]

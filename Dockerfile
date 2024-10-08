FROM openjdk:17-alpine

COPY ./build/libs/malbut-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
FROM openjdk:17
COPY target/spring-boot-jpa-h2-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
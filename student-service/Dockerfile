FROM openjdk:alpine
ADD target/student-service-1.0.0.RELEASE.jar student-service.jar
ENTRYPOINT ["java", "-jar", "/student-service.jar"]
EXPOSE 7000
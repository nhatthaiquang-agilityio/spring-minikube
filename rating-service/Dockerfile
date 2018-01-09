FROM openjdk:alpine
ADD target/rating-service-1.0.0.RELEASE.jar rating-service.jar
ENTRYPOINT ["java", "-jar", "/rating-service.jar"]
EXPOSE 5000
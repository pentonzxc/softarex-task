FROM eclipse-temurin:17-jdk-focal

RUN mkdir -p /usr/src/spring_app/

WORKDIR /usr/src/spring_app/

COPY build/libs/*.jar /usr/src/app/spring-application.jar

EXPOSE 8080

CMD ["java", "-jar" , "spring-application.jar"]
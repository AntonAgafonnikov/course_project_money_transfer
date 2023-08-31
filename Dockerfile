FROM openjdk:17-jdk-slim-buster

EXPOSE 5500

COPY target/course_project_money_transfer-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
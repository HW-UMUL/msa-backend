FROM openjdk:17-alpine
ARG JAR_PATH=target/*.jar
COPY ${JAR_PATH} /home/server.jar
ENTRYPOINT ["java","-jar","/home/server.jar"]
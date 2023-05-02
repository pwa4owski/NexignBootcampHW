FROM openjdk:17

ARG JAR_FILE=target/*.jar

WORKDIR /appdir

COPY ${JAR_FILE} app.jar
RUN mkdir -p "src/main/resources/"

EXPOSE 8090/tcp

ENTRYPOINT ["java","-jar","app.jar", "generate-data"]
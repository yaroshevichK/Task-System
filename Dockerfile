FROM openjdk:17

ARG APP_JAR=target/*.jar
COPY ${APP_JAR} app.jar

ENV POSTGRES_PASSWORD=pasword
ENV POSTGRES_DB=taskdb

ADD ./src/main/resources/init.sql /docker-entrypoint-initdb.d/init.sql

ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080

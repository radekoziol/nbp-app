FROM maven:3.6.0-jdk-8-slim

RUN mkdir /app
WORKDIR /app

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies for offline use
RUN mvn dependency:go-offline -B

COPY ./ .

USER root

RUN mvn spring-boot:run

#
# Build stage
#
FROM maven:3.8.2-openjdk-16 AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY openapi.properties /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM openjdk:17-alpine
WORKDIR /home/app
COPY target/classes /home/app/classes
COPY target/dependency/* /home/app/libs/
EXPOSE 8080
ENTRYPOINT ["java", "-cp", "/home/app/libs/*:/home/app/classes/", "it.gov.pagopa.reportingorgsenrollment.Application"]
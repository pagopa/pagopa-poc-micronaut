#
# Build stage
#
FROM maven:3.8.2-openjdk-16@sha256:0f0c8cfc0718c32f0c8c509403d64eaea54402167251f1150fa51f8f97a9c566 AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY openapi.properties /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM adoptopenjdk/openjdk16:alpine@sha256:3642fda3568dbc3da419f7051e6e59b0f4bd10a42f65b27818d860e6caf1ff3a
COPY --from=build /home/app/target/ReportingOrgsEnrollment-*.jar /usr/local/lib/app.jar
RUN true
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
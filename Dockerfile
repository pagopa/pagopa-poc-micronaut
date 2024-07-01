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
FROM adoptopenjdk/openjdk16:alpine@sha256:988181e80ce8994f102f11513f9181e5a318cb999b80e7a8cef425529554f103
COPY --from=build /home/app/target/ReportingOrgsEnrollment-*.jar /usr/local/lib/app.jar
RUN true
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
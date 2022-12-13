#
# Build stage
#
FROM maven:3.8.2-openjdk-16 AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY openapi.properties /home/app
RUN mvn -f /home/app/pom.xml clean package -Dtest=!it.gov.pagopa.reportingorgsenrollment.controller.EnrollmentControllerTest

#
# Package stage
#
FROM adoptopenjdk/openjdk16:alpine
COPY --from=build /home/app/target/ReportingOrgsEnrollment-*.jar /usr/local/lib/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
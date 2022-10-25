# POC Micronaut - Reporting Organization Enrollment

This project is a Micronaut application for the development of the microservice [ReportingOrgsEnrollment](https://github.com/pagopa/pagopa-reporting-orgs-enrollment).

The aim of this project is the feasibility assessment of referred microservice through Micronaut.

<!-- [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=TODO-set-your-id&metric=alert_status)](https://sonarcloud.io/dashboard?id=TODO-set-your-id) -->

<!-- TODO: generate a index with this tool: https://ecotrust-canada.github.io/markdown-toc/ -->

---
## Api Documentation 📖
See the [OpenApi 3 here.](https://editor.swagger.io/?url=https://raw.githubusercontent.com/pagopa/pagopa-poc-micronaut/main/openapi/openapi.json)

---

## Technology Stack
- Java 11
- Micronaut
- [Azure Table Storage API](https://learn.microsoft.com/en-us/java/api/overview/azure/data-tables-readme?view=azure-java-stable)
---

## Start Project Locally 🚀

### Prerequisites
- docker

### Run docker container

`docker-compose -f ./docker-compose-local.yml up -d`

---

## Develop Locally 💻

### Prerequisites
- git
- maven
- jdk-11

### Run the project

Start the springboot application with this command:

`mvn mn:run`

<!--
### Spring Profiles 

- **local**: to develop locally.
- _default (no profile set)_: The application gets the properties from the environment (for Azure).
-->

### Testing 🧪

#### Unit testing

To run the **Junit** tests:

`mvn clean verify`

#### Integration testing
From `./integration-test/src`

1. `yarn install`
2. `yarn test`

#### Performance testing
install [k6](https://k6.io/) and then from `./performance-test/src`

1. `k6 run --env VARS=local.environment.json --env TEST_TYPE=./test-types/load.json main_scenario.js`


---

## Contributors 👥
Made with ❤️ by PagoPa S.p.A.

### Mainteiners
See `CODEOWNERS` file

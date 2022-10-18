package it.gov.pagopa.reportingorgsenrollement;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Reporting Organizations Enrollments",
                version = "0.0.1",
                description = "PagoPA API",
                license = @License(name = "MIT"),
                contact = @Contact(url = "https://www.pagopa.it/")
        )
)
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}

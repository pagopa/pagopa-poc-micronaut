package it.gov.pagopa.reportingorgsenrollment;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "${openapi.title}",
                version = "${api.version}",
                description = "${openapi.description}",
                license = @License(name = "${license.name}"),
                contact = @Contact(url = "${openapi.contact.url}")
        )
)
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}

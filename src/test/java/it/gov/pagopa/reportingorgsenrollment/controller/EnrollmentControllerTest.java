package it.gov.pagopa.reportingorgsenrollment.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;

import static io.micronaut.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class EnrollmentControllerTest {
    private BlockingHttpClient blockingClient;

    @Inject
    @Client("/")
    HttpClient client;

    @BeforeEach
    void setUp() {
        String uri = "/organizations/mockOrganizationFiscalCode";
        client.toBlocking().exchange(HttpRequest.POST(uri, null));

        blockingClient = client.toBlocking();
    }

    @AfterEach
    void cleanUp(TestInfo testInfo) {
        if(testInfo.getTags().contains("SkipCleanup")) {
            return;
        }

        String uri = "/organizations/mockOrganizationFiscalCode";
        client.toBlocking().exchange(HttpRequest.DELETE(uri, null));
    }

    @Test
    void createOrganization() {
        String uri = "/organizations/mockOrganizationFiscalCode_1";
        HttpResponse<?> response = client.toBlocking().exchange(HttpRequest.POST(uri, null));

        assertNotNull(response);
        assertEquals(CREATED, response.getStatus());
    }

    @Test
    void getOrganization() {
        String uri = "/organizations/mockOrganizationFiscalCode";
        HttpResponse<?> response = blockingClient.exchange(HttpRequest.GET(uri));

        assertNotNull(response.getBody());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void getOrganizations() {
        String uri = "/organizations";
        HttpResponse<?> response = blockingClient.exchange(HttpRequest.GET(uri));

        assertNotNull(response.getBody());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void getOrganizations_methodNotAllowed() {
        String uri = "/organizations";
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () ->
                blockingClient.exchange(HttpRequest.POST(uri, null))
        );

        assertNotNull(thrown.getResponse());
        assertEquals(METHOD_NOT_ALLOWED, thrown.getStatus());
    }

    @Test
    @Tag("SkipCleanup")
    void deleteECEnrollment() {
        String uri = "/organizations/mockOrganizationFiscalCode";
        HttpResponse<?> response = blockingClient.exchange(HttpRequest.DELETE(uri));

        assertNotNull(response.getBody());
        assertEquals(OK, response.getStatus());
    }
}

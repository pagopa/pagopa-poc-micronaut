package it.gov.pagopa.reportingorgsenrollment.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;
import it.gov.pagopa.reportingorgsenrollment.service.EnrollmentsService;
import it.gov.pagopa.reportingorgsenrollment.service.EnrollmentsServiceImpl;
import it.gov.pagopa.reportingorgsenrollment.util.TestUtil;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.micronaut.http.HttpStatus.METHOD_NOT_ALLOWED;
import static io.micronaut.http.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
public class EnrollmentControllerMockTest {

    @Inject
    EnrollmentsService enrollmentsService;

    @Inject
    @Client("/")
    HttpClient client;

    @BeforeEach
    void setUp() {
        when(enrollmentsService.getOrganization(anyString())).thenReturn(TestUtil.getMockOrganizationEntity());
        when(enrollmentsService.createOrganization(anyString())).thenReturn(TestUtil.getMockOrganizationEntity());
        when(enrollmentsService.getOrganizations()).thenReturn(TestUtil.getMockOrganizationEntitySpliterator());
    }

    @Test
    void createOrganization() {
        String uri = "/organizations/mockOrganizationFiscalCode";

        OrganizationModelResponse response = client.toBlocking().retrieve(
                HttpRequest.POST(uri, null),
                OrganizationModelResponse.class
        );

        assertNotNull(response);
        assertEquals(
                TestUtil.getMockOrganizationEntity().getOrganizationFiscalCode(),
                response.getOrganizationFiscalCode()
        );
    }

    @Test
    void getOrganization() {
        String uri = "/organizations/mockOrganizationFiscalCode";

        OrganizationModelResponse response = client.toBlocking().retrieve(
                HttpRequest.GET(uri),
                OrganizationModelResponse.class
        );

        assertNotNull(response);
        assertEquals(
                TestUtil.getMockOrganizationEntity().getOrganizationFiscalCode(),
                response.getOrganizationFiscalCode()
        );
    }

    @Test
    void getOrganizations() {
        String uri = "/organizations";

        List<OrganizationModelResponse> response = client.toBlocking().retrieve(
                HttpRequest.GET(uri),
                List.class
        );

        assertNotNull(response);
        assertTrue(response.toString().contains("mockOrganizationFiscalCode_1"));
        assertTrue(response.toString().contains("mockOrganizationFiscalCode_2"));
        assertTrue(response.toString().contains("mockOrganizationFiscalCode_3"));
    }

    @Test
    void getOrganizations_methodNotAllowed() {
        String uri = "/organizations";

        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () ->
                client.toBlocking().exchange(HttpRequest.POST(uri, null))
        );

        assertNotNull(thrown.getResponse());
        assertEquals(METHOD_NOT_ALLOWED, thrown.getStatus());
    }

    @Test
    void deleteECEnrollment() {
        String uri = "/organizations/mockOrganizationFiscalCode";

        HttpResponse<?> response = client.toBlocking().exchange(HttpRequest.DELETE(uri));

        assertNotNull(response);
        assertEquals(
                OK,
                response.getStatus()
        );
    }

    @MockBean(EnrollmentsServiceImpl.class)
    EnrollmentsService enrollmentsService() {
        return mock(EnrollmentsService.class);
    }
}

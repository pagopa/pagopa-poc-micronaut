package it.gov.pagopa.reportingorgsenrollment.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;
import it.gov.pagopa.reportingorgsenrollment.service.EnrollmentsService;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@ExecuteOn(TaskExecutors.IO)
@Controller()
public class EnrollmentsController implements IEnrollmentsController {

    private final EnrollmentsService enrollmentsService;

    @Inject
    public EnrollmentsController(@Named("EnrollmentsServiceTableImpl") EnrollmentsService enrollmentsService) {
        this.enrollmentsService = enrollmentsService;
    }

    @Override
    public HttpResponse<OrganizationModelResponse> createOrganization(String organizationFiscalCode) {
        return HttpResponse.created(enrollmentsService.createOrganization(organizationFiscalCode));
    }

    @Override
    public HttpResponse<String> removeOrganization(String organizationFiscalCode) {
        enrollmentsService.removeOrganization(organizationFiscalCode);
        return HttpResponse.ok().body("\"The enrollment to reporting service for the organization "+organizationFiscalCode+" was successfully removed\"");
    }

    @Override
    public HttpResponse<OrganizationModelResponse> getOrganization(String organizationFiscalCode) {
        return HttpResponse.ok(enrollmentsService.getOrganization(organizationFiscalCode));
    }

    @Override
    public HttpResponse<List<OrganizationModelResponse>> getOrganizations() {
        return HttpResponse.ok(enrollmentsService.getOrganizations());
    }
}

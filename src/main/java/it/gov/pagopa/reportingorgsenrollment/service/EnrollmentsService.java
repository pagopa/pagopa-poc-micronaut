package it.gov.pagopa.reportingorgsenrollment.service;

import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;

import java.util.List;

public interface EnrollmentsService {
    OrganizationModelResponse createOrganization(String organizationFiscalCode);
    void removeOrganization(String organizationFiscalCode);
    OrganizationModelResponse getOrganization(String organizationFiscalCode);
    List<OrganizationModelResponse> getOrganizations();
}

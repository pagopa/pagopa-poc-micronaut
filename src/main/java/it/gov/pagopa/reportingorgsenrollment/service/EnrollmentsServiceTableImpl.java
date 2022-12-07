package it.gov.pagopa.reportingorgsenrollment.service;

import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;
import it.gov.pagopa.reportingorgsenrollment.repository.OrganizationRepository;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class EnrollementsServiceTableImpl implements EnrollmentsService {

    public EnrollmentsServiceTableImpl() {
    }
    @Override
    public OrganizationModelResponse createOrganization(String organizationFiscalCode) {
        return null;
    }

    @Override
    public void removeOrganization(String organizationFiscalCode) {

    }

    @Override
    public OrganizationModelResponse getOrganization(String organizationFiscalCode) {
        return null;
    }

    @Override
    public List<OrganizationModelResponse> getOrganizations() {
        return null;
    }
}

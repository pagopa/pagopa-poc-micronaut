package it.gov.pagopa.reportingorgsenrollment.util;

import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TestUtil {
	
	private final String mockOrganizationFiscalCode = "mockOrganizationFiscalCode";

	public static OrganizationModelResponse getMockOrganizationEntity() {
		return OrganizationModelResponse.builder().
				organizationFiscalCode(mockOrganizationFiscalCode).
				organizationOnboardingDate(LocalDateTime.now().toString()).
				build();
	}
	
	public static List<OrganizationModelResponse> getMockOrganizationEntitySpliterator() {
		ArrayList<OrganizationModelResponse> list = new ArrayList<>();
		list.add(OrganizationModelResponse.builder().
				organizationFiscalCode(mockOrganizationFiscalCode+"_1").
				organizationOnboardingDate(LocalDateTime.now().toString()).
				build());
		list.add(OrganizationModelResponse.builder().
				organizationFiscalCode(mockOrganizationFiscalCode+"_2").
				organizationOnboardingDate(LocalDateTime.now().toString()).
				build());
		list.add(OrganizationModelResponse.builder().
				organizationFiscalCode(mockOrganizationFiscalCode+"_3").
				organizationOnboardingDate(LocalDateTime.now().toString()).
				build());
		return list;
	}

	
	
}

package it.gov.pagopa.reportingorgsenrollement.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationModelResponse implements Serializable {

	private String organizationFiscalCode;
    private String organizationOnboardingDate;
}

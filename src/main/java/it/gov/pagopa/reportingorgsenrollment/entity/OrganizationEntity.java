package it.gov.pagopa.reportingorgsenrollment.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ORGANIZATION")
public class OrganizationEntity {

    @Id
    @NotNull
    @Column(name = "ORG_FISCAL_CODE", nullable = false, unique = true)
    private String organizationFiscalCode;

    @NotNull
    @Column(name = "ORG_ONBOARDING_DATE", nullable = false)
    private LocalDateTime organizationOnBoardingDate;


    public OrganizationEntity(@NotNull String organizationFiscalCode, LocalDateTime organizationOnBoardingDate) {
        this.organizationFiscalCode = organizationFiscalCode;
        this.organizationOnBoardingDate = organizationOnBoardingDate;
    }
}

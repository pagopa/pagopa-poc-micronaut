package it.gov.pagopa.reportingorgsenrollment;

import io.micronaut.core.convert.TypeConverter;
import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;
import it.gov.pagopa.reportingorgsenrollment.repository.OrganizationRepository;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class EnrollmentsService {

    @Singleton
    TypeConverter<OrganizationEntity, OrganizationModelResponse> organizationResponseTypeConverter() {
        return (object, targetType, context) -> Optional.of(
                OrganizationModelResponse.builder()
                        .organizationFiscalCode(object.getOrganizationFiscalCode())
                        .organizationOnboardingDate(object.getOrganizationOnBoardingDate().toString())
                        .build()
        );
    }
    private final OrganizationRepository organizationRepository;

    EnrollmentsService(OrganizationRepository genreRepository) {
        this.organizationRepository = genreRepository;
    }

    public OrganizationModelResponse createOrganization(String organizationFiscalCode) {
        LocalDateTime organizationOnBoardingDate = LocalDateTime.now();

        OrganizationEntity organizationEntity = organizationRepository.save(organizationFiscalCode, organizationOnBoardingDate);

        return organizationResponseTypeConverter().convert(organizationEntity, OrganizationModelResponse.class).orElse(null);
    }

    public void removeOrganization(String organizationFiscalCode) {
        organizationRepository.deleteByFiscalCode(organizationFiscalCode);
    }

    public OrganizationModelResponse getOrganization(String organizationFiscalCode) {
        OrganizationEntity organizationEntity = organizationRepository.findByFiscalCode(organizationFiscalCode)
                .orElse(null);

        return organizationResponseTypeConverter().convert(organizationEntity, OrganizationModelResponse.class).orElse(null);
    }

    public List<OrganizationModelResponse> getOrganizations() {
        List<OrganizationEntity> organizationEntityList = organizationRepository.findAll();

        return this.convertOrganizationList(organizationEntityList);
    }

    private List<OrganizationModelResponse> convertOrganizationList(List<OrganizationEntity> organizationEntityList) {
        List<OrganizationModelResponse> organizationResponseList = new ArrayList<>();

        for(OrganizationEntity organizationEntity: organizationEntityList)
            organizationResponseList.add(organizationResponseTypeConverter().convert(organizationEntity, OrganizationModelResponse.class).orElse(null));

        return organizationResponseList;
    }
}

package it.gov.pagopa.reportingorgsenrollment.service;

import io.micronaut.core.convert.TypeConverter;
import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import it.gov.pagopa.reportingorgsenrollment.exception.AppError;
import it.gov.pagopa.reportingorgsenrollment.exception.AppException;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;
import it.gov.pagopa.reportingorgsenrollment.repository.OrganizationRepository;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class EnrollmentsService {

    private final OrganizationRepository organizationRepository;

    EnrollmentsService(OrganizationRepository genreRepository) {
        this.organizationRepository = genreRepository;
    }

    public OrganizationModelResponse createOrganization(String organizationFiscalCode) {
        if(organizationRepository.findByFiscalCode(organizationFiscalCode).isEmpty()) {
            LocalDateTime organizationOnBoardingDate = LocalDateTime.now();
            OrganizationEntity organizationEntity = organizationRepository.save(organizationFiscalCode, organizationOnBoardingDate);
            return organizationResponseTypeConverter().convert(organizationEntity, OrganizationModelResponse.class).orElse(null);
        } else
            throw new AppException(AppError.ORGANIZATION_DUPLICATED, organizationFiscalCode);
    }

    public void removeOrganization(String organizationFiscalCode) {
        if( organizationRepository.findByFiscalCode(organizationFiscalCode).isPresent())
            organizationRepository.deleteByFiscalCode(organizationFiscalCode);
        else
            throw new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode);
    }

    public OrganizationModelResponse getOrganization(String organizationFiscalCode) {
        Optional<OrganizationEntity> organizationEntity = organizationRepository.findByFiscalCode(organizationFiscalCode);
        if(organizationEntity.isPresent())
            return organizationResponseTypeConverter().convert(organizationEntity.get(), OrganizationModelResponse.class).orElse(null);
        else
            throw new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode);
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

    TypeConverter<OrganizationEntity, OrganizationModelResponse> organizationResponseTypeConverter() {
        return (object, targetType, context) -> Optional.of(
                OrganizationModelResponse.builder()
                        .organizationFiscalCode(object.getOrganizationFiscalCode())
                        .organizationOnboardingDate(object.getOrganizationOnBoardingDate().toString())
                        .build()
        );
    }
}

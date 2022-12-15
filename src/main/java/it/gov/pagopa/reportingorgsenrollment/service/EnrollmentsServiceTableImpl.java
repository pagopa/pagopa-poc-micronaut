package it.gov.pagopa.reportingorgsenrollment.service;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.TableServiceClientBuilder;
import com.azure.data.tables.models.TableEntity;
import com.azure.data.tables.models.TableServiceException;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.convert.TypeConverter;
import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import it.gov.pagopa.reportingorgsenrollment.exception.AppError;
import it.gov.pagopa.reportingorgsenrollment.exception.AppException;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Singleton
@Slf4j
public class EnrollmentsServiceTableImpl implements EnrollmentsService {

    public final static String FISCAL_CODE_PROPERTY = "organizationFiscalCode";
    public final static String ON_BOARDING_DATE_PROPERTY = "organizationOnBoardingDate";

    private String connectionString;
    private String organizationsTable;

    public EnrollmentsServiceTableImpl(@Value("${tables.reporting.connection}") String connectionString,
                                       @Value("${tables.reporting.organizations.table}") String organizationsTable) {
        this.connectionString = connectionString;
        this.organizationsTable = organizationsTable;
    }

    @Override
    public OrganizationModelResponse createOrganization(String organizationFiscalCode) {
        OrganizationEntity organizationEntity = new OrganizationEntity(organizationFiscalCode, LocalDateTime.now());

        try {
            TableServiceClient tableServiceClient = new TableServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
            TableClient tableClient = tableServiceClient.getTableClient(organizationsTable);
            TableEntity entity = new TableEntity(organizationFiscalCode, organizationFiscalCode)
                    .addProperty(FISCAL_CODE_PROPERTY, organizationEntity.getOrganizationFiscalCode())
                    .addProperty(ON_BOARDING_DATE_PROPERTY, organizationEntity.getOrganizationOnBoardingDate().toString());
            tableClient.createEntity(entity);

            return organizationResponseTypeConverter().convert(organizationEntity, OrganizationModelResponse.class).orElse(null);
        }
        catch (TableServiceException e) {
            log.error("Error in processing create organization", e);
            if (e.getResponse().getStatusCode() == AppError.ORGANIZATION_DUPLICATED.httpStatus.getCode())
                throw new AppException(AppError.ORGANIZATION_DUPLICATED, organizationFiscalCode);
            else
                throw new AppException(AppError.INTERNAL_ERROR, organizationFiscalCode);
        }
    }

    @Override
    public void removeOrganization(String organizationFiscalCode) {
        try {
            TableServiceClient tableServiceClient = new TableServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
            TableClient tableClient = tableServiceClient.getTableClient(organizationsTable);
            // getEntity to be API compliant (delete method is void, but it needs to return 404 if not found: handled by exception)
            tableClient.getEntity(organizationFiscalCode, organizationFiscalCode);
            tableClient.deleteEntity(organizationFiscalCode, organizationFiscalCode);
        }
        catch (TableServiceException e) {
            log.error("Error in processing delete organization", e);
            if (e.getResponse().getStatusCode() == AppError.ORGANIZATION_NOT_FOUND.httpStatus.getCode())
                throw new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode);
            else
                throw new AppException(AppError.INTERNAL_ERROR, organizationFiscalCode);
        }
    }

    @Override
    public OrganizationModelResponse getOrganization(String organizationFiscalCode) {
        try {
            TableServiceClient tableServiceClient = new TableServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
            TableClient tableClient = tableServiceClient.getTableClient(organizationsTable);
            TableEntity tableEntity = tableClient.getEntity(organizationFiscalCode, organizationFiscalCode);
            OrganizationEntity organizationEntity = new OrganizationEntity(
                    tableEntity.getProperty(FISCAL_CODE_PROPERTY).toString(),
                    LocalDateTime.parse(tableEntity.getProperty(ON_BOARDING_DATE_PROPERTY).toString())
            );

            return organizationResponseTypeConverter().convert(organizationEntity, OrganizationModelResponse.class).orElse(null);
        }
        catch (TableServiceException e) {
            log.error("Error in processing get organization", e);
            if (e.getResponse().getStatusCode() == AppError.ORGANIZATION_NOT_FOUND.httpStatus.getCode())
                throw new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode);
            else
                throw new AppException(AppError.INTERNAL_ERROR, organizationFiscalCode);
        }
    }

    @Override
    public List<OrganizationModelResponse> getOrganizations() {
        List<OrganizationModelResponse> organizationModelResponseList = new ArrayList<>();

        try {
            TableServiceClient tableServiceClient = new TableServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
            TableClient tableClient = tableServiceClient.getTableClient(organizationsTable);

            for (TableEntity entity : tableClient.listEntities()) {
                organizationModelResponseList.add(
                        new OrganizationModelResponse(
                                entity.getProperty(FISCAL_CODE_PROPERTY).toString(),
                                entity.getProperty(ON_BOARDING_DATE_PROPERTY).toString()
                        )
                );
            }

            return organizationModelResponseList;
        }
        catch (TableServiceException e) {
            log.error("Error in processing get organizations list", e);
            throw new AppException(AppError.INTERNAL_ERROR, "ALL");
        }
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

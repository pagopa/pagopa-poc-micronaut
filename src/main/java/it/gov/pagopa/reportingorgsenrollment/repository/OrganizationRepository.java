package it.gov.pagopa.reportingorgsenrollment.repository;

import io.micronaut.transaction.annotation.ReadOnly;
import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Singleton
public class OrganizationRepository {
    private final EntityManager entityManager;

    public OrganizationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @ReadOnly
    public Optional<OrganizationEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(OrganizationEntity.class, id));
    }

    @Transactional
    public OrganizationEntity save(@NotBlank String organizationFiscalCode, @NotNull LocalDateTime organizationOnBoardingDate) {
        OrganizationEntity organization = new OrganizationEntity(organizationFiscalCode, organizationOnBoardingDate);
        entityManager.persist(organization);
        return organization;
    }

    @Transactional
    public void deleteById(long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @ReadOnly
    public List<OrganizationEntity> findAll() {
        String qlString = "SELECT orgs FROM OrganizationEntity as orgs";
        TypedQuery<OrganizationEntity> query = entityManager.createQuery(qlString, OrganizationEntity.class);

        return query.getResultList();
    }
}

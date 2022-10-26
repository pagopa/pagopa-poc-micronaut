package it.gov.pagopa.reportingorgsenrollment.repository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
* By default, when using @MicronautTest,
* each @Test method will be wrapped in a transaction
* that will be rolled back when the test finishes.
 */
@MicronautTest
public class OrganizationRepositoryTest {

    @Inject
    private OrganizationRepository organizationRepository;

    @Test
    void testCreateReadOrganization() {
        OrganizationEntity organizationEntity = organizationRepository.save("mockOrganizationFiscalCode", LocalDateTime.now());
        OrganizationEntity savedEntity = organizationRepository.findById(organizationEntity.getId()).orElse(null);

        assertNotNull(savedEntity);
        assertEquals(organizationEntity, savedEntity);
    }

    @Test
    void testReadAllOrganizations() {
        int n = 3;

        for(int i = 0; i < n; i++)
            organizationRepository.save("mockOrganizationFiscalCode" + i, LocalDateTime.now());

        List<OrganizationEntity> organizationEntityList = organizationRepository.findAll();
        assertEquals(n, organizationEntityList.size());
    }

    @Test
    void testDeleteOrganization() {
        int n = 3;
        List<Long> organizationIds = new ArrayList<>();

        for(int i = 0; i < n; i++)
            organizationIds.add(
                    organizationRepository.save("mockOrganizationFiscalCode" + i, LocalDateTime.now()).getId()
            );

        for(long id: organizationIds)
            organizationRepository.deleteById(id);

        assertEquals(0, organizationRepository.findAll().size());
    }
}

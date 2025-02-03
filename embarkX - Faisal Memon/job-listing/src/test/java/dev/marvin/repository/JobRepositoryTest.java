package dev.marvin.repository;

import dev.marvin.TestContainersTest;
import dev.marvin.model.Job;
import dev.marvin.model.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JobRepositoryTest extends TestContainersTest {

    @Autowired
    JobRepository jobRepository;

    private Job savedJob;

    @BeforeEach
    void setUp() {
        Organization organization = Organization.builder()
                .name("TechCorp")
                .description("Insurance & Fintech Solutions")
                .build();

        Job job = Job.builder()
                .title("Software Engineer")
                .description("Backend Software Engineer")
                .maxSalary(new BigDecimal(100000))
                .minSalary(new BigDecimal(80000))
                .organization(organization)
                .build();
        savedJob = jobRepository.save(job);
    }

    @Test
    void getAll() {
        long jobCount = jobRepository.count();

        //when
        jobRepository.findAll();

        //then
        assertEquals(1, jobCount);
    }

    @Test
    void getById() {
        // when
        Optional<Job> retrievedJob = jobRepository.findById(savedJob.getId());

        // then
        assertTrue(retrievedJob.isPresent());
        assertEquals("Software Engineer", retrievedJob.get().getTitle());
        assertEquals(new BigDecimal(100000), retrievedJob.get().getMaxSalary());
    }
}
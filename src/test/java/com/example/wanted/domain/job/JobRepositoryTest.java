package com.example.wanted.domain.job;

import com.example.wanted.domain.RepositoryTest;
import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.company.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Job Repository 테스트")
public class JobRepositoryTest extends RepositoryTest {

    @Autowired JobRepository jobRepository;
    @Autowired CompanyRepository companyRepository;

    Company company;
    Job job;

    @BeforeEach
    void setUp() {
        company = Company.builder()
            .name("원티드")
            .city("서울")
            .country("한국")
            .build();
        Company c1 = companyRepository.save(company);
        job = Job.builder()
            .money(1000)
            .position("벡엔드 개발자")
            .description("채용급구")
            .company(c1)
            .build();
    }

    @AfterEach
    void tearDown() {
        jobRepository.deleteAll();
    }

    @Test
    @DisplayName("Job 을 저장한다. - 성공")
    void saveJob() {
        // when
        Job save = jobRepository.save(job);

        // then
        assertThat(save.getId()).isNotNull();
        assertThat(save.getTech()).isEqualTo(job.getTech());
        assertThat(save.getDescription()).isEqualTo(job.getDescription());
        assertThat(save.getMoney()).isEqualTo(job.getMoney());
    }

    @Test
    @DisplayName("Job 을 조회한다. - 성공")
    void findJob() {
        // given
        Job save = jobRepository.save(job);

        // when
        boolean present = jobRepository.findById(save.getId()).isPresent();

        // then
        assertThat(present).isTrue();
    }

    @Test
    @DisplayName("Job 을 업데이트 한다. - 성공")
    void updateJob() {
        // given
        Job save = jobRepository.save(job);

        // when
        Job updateJob = Job.builder()
            .position("프론트 개발자")
            .tech("python")
            .description("급구하지않음")
            .company(company)
            .money(1L)
            .build();

        // then
        assertThatCode(
            () -> save.update(updateJob)
        ).doesNotThrowAnyException();
    }
}

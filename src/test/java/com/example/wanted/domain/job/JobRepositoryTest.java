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

    @Autowired
    JobRepository jobRepository;
    @Autowired
    CompanyRepository companyRepository;

    Company company;

    @BeforeEach
    void setUp() {
        company = companyRepository.save(Company.builder()
            .id(1L)
            .name("원티드")
            .city("서울")
            .country("한국")
            .build());
    }

    @Test
    @DisplayName("Job 을 저장한다. - 성공")
    void saveJob() {
        // when
        Job job = Job.builder()
            .id(1L)
            .money(1000)
            .position("벡엔드 개발자")
            .description("채용급구")
            .tech("java")
            .company(company)
            .build();

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
        Job job = jobRepository.save(Job.builder()
            .id(1L)
            .money(1000)
            .position("벡엔드 개발자")
            .description("채용급구")
            .tech("java")
            .company(company)
            .build());

        // when
        boolean present = jobRepository.findById(job.getId()).isPresent();

        // then
        assertThat(present).isTrue();
    }

    @Test
    @DisplayName("Job 을 업데이트 한다. - 성공")
    void updateJob() {
        // given
        Job save = jobRepository.saveAndFlush(Job.builder()
            .id(1L)
            .money(1000)
            .position("벡엔드 개발자")
            .description("채용급구")
            .tech("java")
            .company(company)
            .build());

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

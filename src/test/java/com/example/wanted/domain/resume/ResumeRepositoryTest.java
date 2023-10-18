package com.example.wanted.domain.resume;

import com.example.wanted.domain.RepositoryTest;
import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.company.CompanyRepository;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.domain.user.User;
import com.example.wanted.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Resume Repository 테스트")
public class ResumeRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    User user;
    Company company;
    Job job;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
            .id(1L)
            .name("wanted")
            .build());

        company = companyRepository.save(Company.builder()
            .id(1L)
            .name("원티드")
            .country("한국")
            .city("서울")
            .build());

        job = jobRepository.save(Job.builder()
            .id(1L)
            .tech("java")
            .position("벡엔드 개발자")
            .company(company)
            .description("벡엔드 개발자 모집합니다.")
            .money(10_000)
            .build());
    }

    @Test
    @DisplayName("Resume 저장 - 성공")
    void saveResume() {
        // when
        Resume resume = Resume.builder()
            .job(job)
            .user(user)
            .build();

        // then
        assertThatCode(() -> resumeRepository.save(resume)).doesNotThrowAnyException();
        assertThat(resumeRepository.findById(1L).isPresent()).isTrue();
    }

    @Test
    @DisplayName("Job 과 User 로 Resume 존재 여부 찾기 - 성공")
    void findResumeByUseAndJob(){
        // given
        resumeRepository.saveAndFlush(Resume.builder()
            .job(job)
            .user(user)
            .build());

        // when, then
        boolean present = resumeRepository.existsByUserAndJob(user, job);
        assertThat(present).isTrue();
    }
}

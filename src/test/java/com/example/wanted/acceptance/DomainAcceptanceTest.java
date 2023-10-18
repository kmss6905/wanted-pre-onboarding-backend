package com.example.wanted.acceptance;


import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.company.CompanyRepository;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.domain.resume.Resume;
import com.example.wanted.domain.resume.ResumeRepository;
import com.example.wanted.domain.user.User;
import com.example.wanted.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * 최종 인수테스트에 필요한 데이터를 미리 저장합니다.
 */
public class DomainAcceptanceTest extends AcceptanceTest {

    // 도메인과 관련한 인수테스트에 필요한 모든 것들을 미리 구현해 두고 상속받은 하위클래스에서 사용합니다.
    // 매번 하위 클래스에서 구현해서 사용하는 것을 방지하기 위함.

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    protected JobRepository jobRepository;

    @Autowired
    protected ResumeRepository resumeRepository;

    User user = User.builder()
        .id(1L)
        .name("wanted")
        .build();

    User user1 = User.builder()
        .id(2L)
        .name("june")
        .build();

    Company company1 = Company.builder()
        .id(1L)
        .name("원티드랩")
        .country("한국")
        .city("서울")
        .build();

    Company company2 = Company.builder()
        .id(2L)
        .name("네이버")
        .country("한국")
        .city("판교")
        .build();

    Company company3 = Company.builder()
        .id(3L)
        .name("원티드코리아")
        .country("한국")
        .city("부산")
        .build();

    Company company4 = Company.builder()
        .id(4L)
        .name("카카오")
        .city("한국")
        .country("판교")
        .build();


    @BeforeEach
    protected void setUp() {
        companyRepository.saveAll(List.of(company1, company2, company3, company4));
        userRepository.saveAll(List.of(user, user1));
    }

    protected void saveJob() {
        Company company = companyRepository.findById(company1.getId()).get();
        Job job = Job.builder()
            .id(1L)
            .company(company)
            .money(10_000L)
            .tech("java")
            .position("벡엔드 개발자")
            .description("채용 급구")
            .build();
        jobRepository.saveAndFlush(job);
    }

    protected void saveJobsWithCountBy(int count) {
        Company company = companyRepository.findById(company1.getId()).get();
        List<Job> jobsToSave = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            jobsToSave.add(createJobWithCompany(i, company));
        }
        jobRepository.saveAllAndFlush(jobsToSave);
    }

    private static Job createJobWithCompany(long jobId, Company company) {
        return Job.builder()
            .id(jobId)
            .company(company)
            .money(10_000L)
            .tech("java")
            .position("벡엔드 개발자")
            .description("채용 급구")
            .build();
    }

    protected void addResume() {
        saveJob();
        User userSaved = userRepository.findById(1L).get();
        Job jobSaved = jobRepository.findById(1L).get();
        Resume resume = Resume.builder()
            .user(userSaved)
            .job(jobSaved)
            .build();
        resumeRepository.saveAndFlush(resume);
    }
}

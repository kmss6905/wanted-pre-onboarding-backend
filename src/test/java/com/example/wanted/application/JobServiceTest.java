package com.example.wanted.application;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.company.CompanyRepository;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.dto.job.AddJobRequest;
import com.example.wanted.dto.job.UpdateJobRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@MockitoSettings
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private JobService jobService;

    Company company;

    @BeforeEach
    void setUp() {
        company = Company.builder()
            .id(1L)
            .name("원티드")
            .city("서울")
            .country("한국")
            .build();
    }

    @Test
    void addJob() {
        // given
        given(companyRepository.findById(any())).willReturn(Optional.of(company));

        // when

        AddJobRequest addJobRequest = AddJobRequest.builder()
            .companyId(1L)
            .compensateMoneyValue(1_000L)
            .tech("python")
            .position("벡엔드 개발자")
            .desc("채용급구")
            .build();

        jobService.addJob(addJobRequest);

        // then
        then(companyRepository)
            .should(times(1))
            .findById(company.getId());
        then(jobRepository)
            .should(times(1))
            .save(any());
    }

    @Test
    void updateJob() {
        // given
        UpdateJobRequest jobRequest = UpdateJobRequest.builder()
            .position("프론트 개발자")
            .desc("안급구")
            .tech("java")
            .compensateMoneyValue(100L)
            .build();

        Job job = Job.builder()
            .id(1L)
            .tech("java")
            .position("벡엔드 개발자")
            .description("급구")
            .money(1L)
            .company(company)
            .build();

        given(jobRepository.findById(1L)).willReturn(Optional.of(job));

        // when
        jobService.updateJob(1L, jobRequest);

        // then
        then(jobRepository).should(times(1)).findById(1L);
        assertEquals("js", job.getTech());
        assertEquals("프론트 개발자", job.getPosition());
        assertEquals("안급구", job.getDescription());
        assertEquals(100L, job.getMoney());
    }

    @Test
    void deleteJob() {
    }

    @Test
    void findJobs() {
    }

    @Test
    void findOneJob() {
    }
}
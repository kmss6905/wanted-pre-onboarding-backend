package com.example.wanted.application;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.company.CompanyRepository;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.dto.job.AddJobRequest;
import com.example.wanted.dto.job.UpdateJobRequest;
import com.example.wanted.exception.company.CompanyNotFoundException;
import com.example.wanted.exception.job.JobNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@MockitoSettings
@DisplayName("JobService 테스트")
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private JobService jobService;

    Company company;
    UpdateJobRequest updateJobRequest;
    Job job;

    @BeforeEach
    void setUp() {
        company = Company.builder()
            .id(1L)
            .name("원티드")
            .city("서울")
            .country("한국")
            .build();

        updateJobRequest = UpdateJobRequest.builder()
            .position("프론트 개발자")
            .desc("안급구")
            .tech("java")
            .compensateMoneyValue(100L)
            .build();

        job = Job.builder()
            .id(1L)
            .money(1000)
            .position("벡엔드 개발자")
            .description("채용급구")
            .tech("java")
            .company(company)
            .build();
    }

    @Test
    @DisplayName("유효한 채용공고(Job) 추가 - 성공")
    void addJob() {
        // given
        given(companyRepository.findById(any())).willReturn(Optional.of(company));

        // when
        AddJobRequest addJobRequest = createAddJobRequest();
        jobService.addJob(addJobRequest);

        // then
        then(companyRepository).should(times(1)).findById(company.getId());
        then(jobRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("유효하지 않는 회사 ID 일 때 채용공고(Job) 추가 - 실패")
    void invalidCompanyId() {
        // given
        given(companyRepository.findById(any())).willReturn(Optional.empty());

        // when
        AddJobRequest addJobRequest = createAddJobRequest();


        // then
        assertThatThrownBy(() -> jobService.addJob(addJobRequest))
            .isInstanceOf(CompanyNotFoundException.class);

        then(companyRepository).should(times(1)).findById(1L);
        then(jobRepository).should(times(0)).save(any(Job.class));
    }


    private AddJobRequest createAddJobRequest() {
        return AddJobRequest.builder()
            .companyId(1L)
            .compensateMoneyValue(1_000L)
            .tech("python")
            .position("벡엔드 개발자")
            .desc("채용급구")
            .build();
    }

    @Test
    @DisplayName("유효한 채용공고(Job) 수정 - 성공")
    void updateJob() {
        // given
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
        jobService.updateJob(1L, updateJobRequest);

        // then
        then(jobRepository).should(times(1)).findById(1L);
        assertThat("java").isEqualTo(job.getTech());
        assertThat("프론트 개발자").isEqualTo(job.getPosition());
        assertThat("안급구").isEqualTo(job.getDescription());
        assertThat(100L).isEqualTo(job.getMoney());
    }

    @Test
    @DisplayName("유효하지 않는 JobId 로 채용공고(Job) 수정 - 실패")
    void invalidJob() {
        // given
        when(jobRepository.findById(any())).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> jobService.updateJob(2L, updateJobRequest))
            .isInstanceOf(JobNotFoundException.class);

        then(jobRepository).should(times(1)).findById(any());

        // 바뀌지 않고 그대로
        assertThat(job.getTech()).isEqualTo("java");
        assertThat(job.getPosition()).isEqualTo("벡엔드 개발자");
        assertThat(job.getDescription()).isEqualTo("채용급구");
        assertThat(job.getMoney()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("채용공고(Job) 삭제 - 성공")
    void deleteJob() {
        // given
        when(jobRepository.findById(any())).thenReturn(Optional.of(job));

        // when
        jobService.deleteJob(1L);

        // then
        then(jobRepository).should(times(1)).delete(job);
    }
}
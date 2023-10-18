package com.example.wanted.application;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.domain.resume.Resume;
import com.example.wanted.domain.resume.ResumeRepository;
import com.example.wanted.domain.user.User;
import com.example.wanted.domain.user.UserRepository;
import com.example.wanted.dto.resume.ResumeRequest;
import com.example.wanted.exception.apply.AlreadyResumeSubmittedException;
import com.example.wanted.exception.job.JobNotFoundException;
import com.example.wanted.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

@MockitoSettings
@DisplayName("ResumeService 테스트")
public class ResumeServiceTest {

    @Mock
    JobRepository jobRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ResumeRepository resumeRepository;

    @InjectMocks
    ResumeService resumeService;

    Job job;
    User user;
    Company company;

    @BeforeEach
    void setUp() {
        company = Company.builder()
            .id(1L)
            .name("원티드")
            .city("서울")
            .country("한국")
            .build();

        job = Job.builder()
            .id(1L)
            .money(1000)
            .position("벡엔드 개발자")
            .description("채용급구")
            .tech("java")
            .company(company)
            .build();

        user = User.builder()
            .id(1L)
            .name("wanted")
            .build();
    }

    @Test
    @DisplayName("유효한 지원 테스트")
    void validApplyJob() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(jobRepository.findById(1L)).willReturn(Optional.of(job));
        given(resumeRepository.existsByUserAndJob(any(), any())).willReturn(false);

        ResumeRequest resumeRequest = ResumeRequest.builder()
            .jobId(1L)
            .userId(1L)
            .build();

        // when
        resumeService.applyJob(resumeRequest);

        // then
        then(jobRepository).should(times(1)).findById(1L);
        then(userRepository).should(times(1)).findById(1L);
        then(resumeRepository).should(times(1)).existsByUserAndJob(user, job);
        then(resumeRepository).should(times(1)).save(any(Resume.class));
    }

    @Test
    @DisplayName("유효하지 않은 사용자 ID 테스트")
    void invalidUserIdApplyJob() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        ResumeRequest resumeRequest = ResumeRequest.builder()
            .jobId(1L)
            .userId(1L)
            .build();

        // when
        assertThatThrownBy(() -> resumeService.applyJob(resumeRequest))
            .isInstanceOf(UserNotFoundException.class);

        // then
        then(jobRepository).should(times(0)).findById(1L);
        then(userRepository).should(times(1)).findById(1L);
    }

    @Test
    @DisplayName("유효하지 않은 작업 ID 테스트")
    void invalidJobIdApplyJob() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(jobRepository.findById(1L)).willReturn(Optional.empty());

        ResumeRequest resumeRequest = ResumeRequest.builder()
            .jobId(1L)
            .userId(1L)
            .build();

        // when
        assertThatThrownBy(() -> resumeService.applyJob(resumeRequest))
            .isInstanceOf(JobNotFoundException.class);

        // then
        then(jobRepository).should(times(1)).findById(1L);
        then(userRepository).should(times(1)).findById(1L);
    }

    @Test
    @DisplayName("동일한 사용자가 두 번 이상 지원하는 경우 테스트")
    void duplicateApplication() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(jobRepository.findById(1L)).willReturn(Optional.of(job));
        given(resumeRepository.existsByUserAndJob(any(), any())).willReturn(true);

        ResumeRequest resumeRequest = ResumeRequest.builder()
            .jobId(1L)
            .userId(1L)
            .build();

        // when, then
        assertThatThrownBy(() -> resumeService.applyJob(resumeRequest))
            .isInstanceOf(AlreadyResumeSubmittedException.class);

        then(userRepository).should(times(1)).findById(1L);
        then(jobRepository).should(times(1)).findById(1L);
        then(resumeRepository).should(times(1)).existsByUserAndJob(user, job);
    }
}

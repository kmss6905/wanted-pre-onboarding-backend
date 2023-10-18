package com.example.wanted.domain.resume;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


@DisplayName("JobApplication 도메인 테스트")
class ResumeTest {

    Job job;
    Job job1;
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

        job1 = Job.builder()
            .id(2L)
            .money(1000)
            .position("벡엔드 개발자")
            .description("채용급구")
            .tech("java")
            .company(company)
            .build();

        user = User.builder()
            .name("wanted")
            .build();
    }

    @Test
    @DisplayName("Builder 를 이용해 JobApplication 생성 - 성공")
    void createJobApplication() {
        assertThatCode(() -> Resume.builder()
            .job(job)
            .user(user)
            .build()
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Job 이 Null 일 때 JobApplication 생성 - 실패")
    void throwExceptionJobIsNull() {
        assertThatThrownBy(() -> Resume.builder()
            .job(null)
            .user(user)
            .build()
        );
    }

    @Test
    @DisplayName("User 가 Null 일 때 JobApplication 생성 - 실패")
    void throwExceptionUserIsNull() {
        assertThatThrownBy(() -> Resume.builder()
            .job(job)
            .user(null)
            .build()
        );
    }

    @Test
    @DisplayName("JobApplication 의 Job 과 같은 Job 이 오면 True 반환 - 성공 ")
    void shouldReturnTrueWhenDifferJobWithJobApplicationJob() {
        // given
        Resume resume = Resume.builder()
            .job(job)
            .user(user)
            .build();

        // when
        boolean isMatchesJob = resume.matchesJob(job);

        // then
        assertThat(isMatchesJob).isTrue();
    }

    @Test
    @DisplayName("JobApplication 의 Job 과 같은 Job 이 오면 False 반환 - 성공 ")
    void shouldReturnFalseWhenDifferJobWithJobApplicationJob() {
        // given
        Resume resume = Resume.builder()
            .job(job)
            .user(user)
            .build();

        // when
        boolean isMatchesJob = resume.matchesJob(job1);

        // then
        assertThat(isMatchesJob).isFalse();
    }
}
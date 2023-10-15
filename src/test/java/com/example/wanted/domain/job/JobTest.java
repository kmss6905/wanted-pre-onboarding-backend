package com.example.wanted.domain.job;

import com.example.wanted.domain.company.Company;
import com.example.wanted.exception.job.UpdateJobNullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JobTest {

    @Test
    @DisplayName("Builder 를 이용해서 Job 을 생성한다.")
    void createJobWithBuilder() {
        // given
        Company company = Company.builder()
            .id(1L)
            .name("원티드")
            .country("한국")
            .city("서울")
            .build();

        // when, then
        assertThatCode(() -> Job.builder()
            .tech("java")
            .position("벡엔드 개발자")
            .company(company)
            .description("벡엔드 개발자 모집합니다.")
            .money(10_000)
            .build()
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Company 가 없으면 Job 을 생성할 수 없다.")
    void throwExceptionWhenJobCreatedWithoutCompany() {
        // when, then
        assertThatThrownBy(() -> Job.builder()
            .tech("java")
            .position("벡엔드 개발자")
            .company(null)
            .description("벡엔드 개발자 모집합니다.")
            .money(10_000)
            .build()
        );
    }

    @Test
    @DisplayName("업데이트 할 Job 이 없으면 업데이트 할 수 없다.")
    void throwExceptionWhenJobUpdatedWithoutJob() {
        // given
        Company company = Company.builder()
            .id(1L)
            .name("원티드")
            .country("한국")
            .city("서울")
            .build();

        Job job = Job.builder()
            .tech("java")
            .position("벡엔드 개발자")
            .company(company)
            .description("벡엔드 개발자 모집합니다.")
            .money(10_000)
            .build();


        // when, then
        assertThatThrownBy(() -> job.update(null))
            .isInstanceOf(UpdateJobNullException.class);
    }
}
package com.example.wanted.dto.job;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("'채용공고 수정 요청' 객체 테스트")
class UpdateJobRequestTest {

    UpdateJobRequest updateJobRequest;

    @Test
    @DisplayName("UpdateJobRequest 에서 Job 구성 - 성공")
    void toJob() {
        // given
        updateJobRequest = UpdateJobRequest.builder()
            .tech("java")
            .position("벡앤드 개발자")
            .compensateMoneyValue(1L)
            .desc("채용 급구")
            .build();

        // when, then
        assertThatCode(() -> updateJobRequest.toJob())
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Builder 를 이용해서 JobRequest 만들기")
    void createUpdateJobRequest() {
        // when, then
        assertThatCode(() -> UpdateJobRequest.builder()
            .tech("java")
            .position("벡앤드 개발자")
            .compensateMoneyValue(1L)
            .desc("채용 급구")
            .build()
        ).doesNotThrowAnyException();
    }
}
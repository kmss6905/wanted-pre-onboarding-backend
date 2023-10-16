package com.example.wanted.dto.job;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class AddJobRequestTest {

    @Test
    void toJobWithCompany() {

    }

    @Test
    void getCompanyId() {
    }

    @Test
    @DisplayName("Builder 를 이용하여 AddJobRequest 를 생성한다. - 성공")
    void createAddJobRequest() {
        assertThatCode(() -> AddJobRequest.builder()
                .position("벡엔드 개발자")
                .tech("java")
                .companyId(1L)
                .compensateMoneyValue(100L)
                .desc("채용급구")
                .build()
        ).doesNotThrowAnyException();
    }
}
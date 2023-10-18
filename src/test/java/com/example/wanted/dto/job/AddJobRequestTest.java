package com.example.wanted.dto.job;

import com.example.wanted.domain.company.Company;
import com.example.wanted.exception.job.CompanyNullException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("'채용공고 추가 요청' 객체 테스트")
class AddJobRequestTest {

    AddJobRequest addJobRequest;

    @BeforeEach
    void setUp() {
        addJobRequest = AddJobRequest.builder()
            .desc("채용 급구")
            .companyId(1L)
            .tech("java")
            .compensateMoneyValue(1L)
            .position("벡엔드 개발자")
            .build();
    }

    @Test
    @DisplayName("AddJobRequest 에서 Job 구성 - 성공")
    void toJobWithCompany() {
        Company company = Company.builder()
            .id(1L)
            .name("원티드")
            .city("서울")
            .country("한국")
            .build();
        assertThatCode(() -> addJobRequest.toJobWithCompany(company))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("AddJobRequest 에서 Company 없이 Job 구성 - 실패")
    void toJobWithCompanyWithoutCompany() {
        // given
        AddJobRequest jobRequest = AddJobRequest.builder()
            .desc("채용 급구")
            .companyId(1L)
            .tech("java")
            .compensateMoneyValue(1L)
            .position("벡엔드 개발자")
            .build();

        assertThatThrownBy(() -> jobRequest.toJobWithCompany(null))
            .isInstanceOf(CompanyNullException.class);
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
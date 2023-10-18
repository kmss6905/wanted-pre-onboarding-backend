package com.example.wanted.domain.job;

import com.example.wanted.domain.company.Company;
import com.example.wanted.exception.job.UpdateJobNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Job 도메인 테스트")
class JobTest {

    private static Stream<String> blankOrNullAndEmptyStrings() {
        return Stream.of(null, "", " ", "   ", "\t", "\n");
    }

    Company company;

    @BeforeEach
    void setUp() {
        // given
        company = Company.builder()
            .id(1L)
            .name("원티드")
            .country("한국")
            .city("서울")
            .build();
    }

    @Test
    @DisplayName("Builder 를 이용해서 Job 을 생성한다. - 성공")
    void createJobWithBuilder() {
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

    @DisplayName("tech 이 Null,Blank,Empty 이면 Job 객체 생성 - 실패")
    @ParameterizedTest
    @MethodSource("blankOrNullAndEmptyStrings")
    void createJobWithBuilderWithoutTech(String text) {
        assertThatThrownBy(() -> Job.builder()
            .tech(text)
            .position("벡엔드 개발자")
            .company(company)
            .description("벡엔드 개발자 모집합니다.")
            .money(10_000)
            .build()
        );
    }

    @DisplayName("tech 이 Null,Blank,Empty 이면 Job 객체 생성 - 실패")
    @ParameterizedTest
    @MethodSource("blankOrNullAndEmptyStrings")
    void createJobWithBuilderWithoutPosition(String text) {
        assertThatThrownBy(() -> Job.builder()
            .tech("java")
            .position(text)
            .company(company)
            .description("벡엔드 개발자 모집합니다.")
            .money(10_000)
            .build()
        );
    }

    @DisplayName("description 이 Null,Blank,Empty 이면 Job 객체 생성 실패")
    @ParameterizedTest
    @MethodSource("blankOrNullAndEmptyStrings")
    void createJobWithBuilderWithoutDesc(String text) {
        assertThatThrownBy(() -> Job.builder()
            .tech("java")
            .position("벡엔드 개발자")
            .company(company)
            .description(text)
            .money(10_000)
            .build()
        );
    }


    @Test
    @DisplayName("업데이트 할 Job 이 없으면 업데이트 할 수 없다.")
    void throwExceptionWhenJobUpdatedWithoutJob() {
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
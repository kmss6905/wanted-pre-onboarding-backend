package com.example.wanted.dto.resume;

import com.example.wanted.dto.ValidationTest;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DisplayName("'지원서 제출' 요청 객체 테스트 ")
class ResumeRequestTest extends ValidationTest {

    @Test
    @DisplayName("Builder 를 이용하여 ApplyJobRequest 생성하기")
    void builder() {
        assertThatCode(() -> ResumeRequest.builder()
            .jobId(1L)
            .userId(1L)
            .build()
        ).doesNotThrowAnyException();
    }

    @Test
    void testValidApplyJobRequest() {
        // when
        ResumeRequest validRequest = ResumeRequest.builder()
            .jobId(1L)
            .userId(2L)
            .build();
        Set<ConstraintViolation<ResumeRequest>> violations = validator.validate(validRequest);

        // then
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("JobId는 최소 1 이상이어야 합니다.")
    void testInvalidJobId() {
        // when
        ResumeRequest invalidRequest = ResumeRequest.builder()
            .jobId(0L)
            .userId(2L)
            .build();
        Set<ConstraintViolation<ResumeRequest>> violations = validator.validate(invalidRequest);

        // then
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<ResumeRequest> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("최소 1 이어야 합니다.");
    }

    @Test
    @DisplayName("userId 는 최소 1 이상이어야 합니다.")
    void testInvalidUserId() {
        // when
        ResumeRequest invalidRequest = ResumeRequest.builder()
            .jobId(1L)
            .userId(0L)
            .build();
        Set<ConstraintViolation<ResumeRequest>> violations = validator.validate(invalidRequest);

        // then
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<ResumeRequest> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("최소 1 이어야 합니다.");
    }
}
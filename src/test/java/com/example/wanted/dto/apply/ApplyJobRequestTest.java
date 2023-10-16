package com.example.wanted.dto.apply;

import com.example.wanted.dto.ValidationTest;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class ApplyJobRequestTest extends ValidationTest {

    @Test
    @DisplayName("Builder 를 이용하여 ApplyJobRequest 생성하기")
    void builder() {
        assertThatCode(() -> ApplyJobRequest.builder()
            .jobId(1L)
            .userId(1L)
            .build()
        ).doesNotThrowAnyException();
    }

    @Test
    void testValidApplyJobRequest() {
        // when
        ApplyJobRequest validRequest = ApplyJobRequest.builder()
            .jobId(1L)
            .userId(2L)
            .build();
        Set<ConstraintViolation<ApplyJobRequest>> violations = validator.validate(validRequest);

        // then
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("JobId는 최소 1 이상이어야 합니다.")
    void testInvalidJobId() {
        // when
        ApplyJobRequest invalidRequest = ApplyJobRequest.builder()
            .jobId(0L)
            .userId(2L)
            .build();
        Set<ConstraintViolation<ApplyJobRequest>> violations = validator.validate(invalidRequest);

        // then
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<ApplyJobRequest> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("jobId value is 1");
    }

    @Test
    void testInvalidUserId() {
        // when
        ApplyJobRequest invalidRequest = ApplyJobRequest.builder()
            .jobId(1L)
            .userId(0L)
            .build();
        Set<ConstraintViolation<ApplyJobRequest>> violations = validator.validate(invalidRequest);

        // then
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<ApplyJobRequest> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("userId min value is 1");
    }
}
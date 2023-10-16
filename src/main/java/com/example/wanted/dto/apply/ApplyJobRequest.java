package com.example.wanted.dto.apply;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ApplyJobRequest {
    @NonNull
    @Min(value = 1L, message = "jobId value is 1")
    private Long jobId;

    @NonNull
    @Min(value = 1L, message = "userId min value is 1")
    private Long userId;
}

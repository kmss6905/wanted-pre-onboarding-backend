package com.example.wanted.dto.job;

import com.example.wanted.domain.job.Job;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateJobRequest {

    @NotNull
    @NotBlank
    private String position;

    @NotNull
    @NotBlank
    private String desc;

    @NotNull
    @NotBlank
    private String tech;

    @NotNull
    @Min(value = 10_000, message = "최소 {value} 이어야 합니다.")
    @Max(value = 10_000_000, message = "최대 {value} 이어야 합니다.")
    private Long compensateMoneyValue;

    public Job toJob() {
        return Job.builder()
            .position(position)
            .description(desc)
            .money(compensateMoneyValue)
            .tech(tech)
            .build();
    }
}

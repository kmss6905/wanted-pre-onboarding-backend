package com.example.wanted.dto.job;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.job.Job;
import com.example.wanted.exception.job.CompanyNullException;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Objects;

@Builder
@AllArgsConstructor
public class AddJobRequest {

    @NotNull
    @Min(value = 1, message = "최소 {value} 이어야 합니다.")
    private Long companyId;

    @NotNull
    @Min(value = 10_000, message = "최소 {value} 이어야 합니다.")
    @Max(value = 10_000_000, message = "최대 {value} 이어야 합니다.")
    private Long compensateMoneyValue;

    @NotNull
    @NotBlank
    private String position;

    @NotNull
    @NotBlank
    private String desc;

    @NotNull
    @NotBlank
    private String tech;

    public Job toJobWithCompany(Company company) {
        if (Objects.isNull(company)) {
            throw new CompanyNullException();
        }
        return Job.builder()
            .company(company)
            .money(compensateMoneyValue)
            .position(position)
            .description(desc)
            .tech(tech)
            .build();
    }

    public long getCompanyId() {
        return companyId;
    }
}

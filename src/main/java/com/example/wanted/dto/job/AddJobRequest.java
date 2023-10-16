package com.example.wanted.dto.job;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.job.Job;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class AddJobRequest {

    @NotNull(message = "cant null")
    @Min(value = 1, message = "Company ID must not be zero")
    private Long companyId;

    @NotNull(message = "cant null")
    @Min(value = 10_000, message = "money value is must be least 10,000")
    private Long compensateMoneyValue;

    @NotNull(message = "cant null")
    @NotBlank(message = "position is can't not be blank")
    private String position;

    @NotNull(message = "cant null")
    @NotBlank(message = "desc is can't not be blank")
    private String desc;

    @NotNull(message = "cant null")
    @NotBlank(message = "tech is can't not be blank")
    private String tech;

    public Job toJobWithCompany(Company company) {
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

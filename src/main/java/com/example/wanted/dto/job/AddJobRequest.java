package com.example.wanted.dto.job;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.job.Job;
import lombok.Builder;

@Builder
public class AddJobRequest {
    private long companyId;
    private long compensateMoneyValue;
    private String position;
    private String desc;
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

package com.example.wanted.dto.job;

import com.example.wanted.domain.job.Job;
import lombok.Builder;

@Builder
public class UpdateJobRequest {
    private long compensateMoneyValue;
    private String position;
    private String desc;
    private String tech;

    public Job toJob() {
        return Job.builder()
            .position(position)
            .description(desc)
            .money(compensateMoneyValue)
            .tech(tech)
            .build();
    }
}

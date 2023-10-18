package com.example.wanted.dto.job;

import com.example.wanted.domain.job.Job;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * {
 * 		"채용공고_id": 채용공고_id,
 * 	  "회사명":"원티드랩",
 * 	  "국가":"한국",
 * 	  "지역":"서울",
 * 	  "채용포지션":"백엔드 주니어 개발자",
 * 	  "채용보상금":1500000,
 * 	  "사용기술":"Python"
 *        },
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobResponse {
    private long jobId;
    private String companyName;
    private String companyCountry;
    private String companyCity;
    private String position;
    private long money;
    private String tech;
    private String description;
    private List<Long> anotherJobsId;

    public static JobResponse of(final Job job) {
        return JobResponse.builder()
            .jobId(job.getId())
            .tech(job.getTech())
            .companyName(job.getCompany().getName())
            .companyCountry(job.getCompany().getCountry())
            .companyCity(job.getCompany().getCity())
            .position(job.getPosition())
            .money(job.getMoney())
            .build();
    }

    public static JobResponse ofWithDescOtherJobIds(final Job job, final List<Long> ids) {
        return JobResponse.builder()
            .jobId(job.getId())
            .companyCity(job.getCompany().getCity())
            .companyCountry(job.getCompany().getCountry())
            .companyName(job.getCompany().getName())
            .money(job.getMoney())
            .tech(job.getTech())
            .position(job.getPosition())
            .description(job.getDescription())
            .anotherJobsId(ids)
            .build();
    }
}

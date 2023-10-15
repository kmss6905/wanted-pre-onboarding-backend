package com.example.wanted.dto.job;

import com.example.wanted.domain.job.Job;
import lombok.Builder;

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
public class JobResponse {
    private long jobId;
    private String companyName;
    private String companyCountry;
    private String companyNation;
    private String position;
    private long money;
    private String tech;
    private String description;
    private List<Integer> anotherJobsId;

    public static JobResponse of(final Job job) {
        return JobResponse.builder()
            .jobId(job.getId())
            .tech(job.getTech())
            .companyName(job.getCompany().getName())
            .companyNation(job.getCompany().getCity())
            .money(job.getMoney())
            .build();
    }

    public static JobResponse ofWtihDesc(final Job job) {
        return JobResponse.builder()
            .jobId(job.getId())
            .companyNation(job.getCompany().getCity())
            .companyCountry(job.getCompany().getCountry())
            .companyName(job.getCompany().getName())
            .tech(job.getTech())
            .position(job.getPosition())
            .description(job.getDescription())
            .build();
    }
}

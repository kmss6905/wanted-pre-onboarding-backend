package com.example.wanted.application;

import com.example.wanted.domain.BaseEntity;
import com.example.wanted.domain.company.CompanyRepository;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.dto.job.JobResponse;
import com.example.wanted.exception.job.JobNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobSearchService {
    private final JobRepository jobRepository;

    public JobSearchService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Transactional(readOnly = true)
    public List<JobResponse> searchJobsWithPaging(String text, Pageable pageable) {
        Page<Job> jobs = jobRepository.findJobsBySearchCondition(text, pageable);
        return jobs.stream()
            .map(JobResponse::of)
            .toList();
    }

    @Transactional(readOnly = true)
    public JobResponse findOneJob(long jobId) {
        Job job = findJob(jobId);
        List<Long> jobIds = job.getCompany().getJobs()
            .stream()
            .map(BaseEntity::getId)
            .filter(it -> !it.equals(job.getId()))
            .toList();
        return JobResponse.ofWithDescOtherJobIds(job, jobIds);
    }

    private Job findJob(long jobId) {
        return jobRepository.findById(jobId)
            .orElseThrow(JobNotFoundException::new);
    }
}

package com.example.wanted.application;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.company.CompanyRepository;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.dto.job.AddJobRequest;
import com.example.wanted.dto.job.JobResponse;
import com.example.wanted.dto.job.UpdateJobRequest;
import com.example.wanted.exception.company.NotFoundCompanyException;
import com.example.wanted.exception.job.NotFoundJobException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void addJob(AddJobRequest addJobRequest) {
        Company company = findCompany(addJobRequest);
        Job job = addJobRequest.toJobWithCompany(company);
        jobRepository.save(job);
    }

    private Company findCompany(AddJobRequest addJobRequest) {
        return companyRepository.findById(addJobRequest.getCompanyId())
            .orElseThrow(NotFoundCompanyException::new);
    }

    @Transactional
    public void updateJob(long jobId, UpdateJobRequest updateJobRequest) {
        Job job = findJob(jobId);
        job.update(updateJobRequest.toJob());
    }

    private Job findJob(long jobId) {
        return jobRepository.findById(jobId)
            .orElseThrow(NotFoundJobException::new);
    }

    public void deleteJob(long jobId) {
        jobRepository.deleteById(jobId);
    }

    @Transactional(readOnly = true)
    public List<JobResponse> findJobs(Pageable pageable) {
        return jobRepository.findAll(pageable).stream()
            .map(JobResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JobResponse findOneJob(long jobId) {
        Job job = findJob(jobId);
        return JobResponse.ofWtihDesc(job);
    }
}

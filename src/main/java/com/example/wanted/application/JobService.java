package com.example.wanted.application;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.company.CompanyRepository;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.dto.job.AddJobRequest;
import com.example.wanted.dto.job.JobResponse;
import com.example.wanted.dto.job.UpdateJobRequest;
import com.example.wanted.exception.company.CompanyNotFoundException;
import com.example.wanted.exception.job.JobNotFoundException;
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
            .orElseThrow(CompanyNotFoundException::new);
    }

    @Transactional
    public void updateJob(long jobId, UpdateJobRequest updateJobRequest) {
        Job job = findJob(jobId);
        job.update(updateJobRequest.toJob());
    }

    private Job findJob(long jobId) {
        return jobRepository.findById(jobId)
            .orElseThrow(JobNotFoundException::new);
    }

    @Transactional
    public void deleteJob(long jobId) {
        Job job = findJob(jobId);
        jobRepository.delete(job);
    }
}

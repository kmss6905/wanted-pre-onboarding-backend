package com.example.wanted.application;

import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobApplication;
import com.example.wanted.domain.job.JobApplicationRepository;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.domain.user.User;
import com.example.wanted.domain.user.UserRepository;
import com.example.wanted.dto.apply.ApplyJobRequest;
import com.example.wanted.exception.ApplicationAlreadyExistsException;
import com.example.wanted.exception.job.NotFoundJobException;
import com.example.wanted.exception.user.NotFoundUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplyService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplyService(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void applyJob(ApplyJobRequest applyJobRequest) {
        User user = findUser(applyJobRequest);
        Job job = findJob(applyJobRequest);
        if (user.isAppliedForJob(job)) {
            throw new ApplicationAlreadyExistsException();
        }
        user.applyJob(job);
    }

    private User findUser(ApplyJobRequest applyJobRequest) {
        return userRepository.findById(applyJobRequest.getUserId())
            .orElseThrow(NotFoundUserException::new);
    }

    private Job findJob(ApplyJobRequest applyJobRequest) {
        return jobRepository.findById(applyJobRequest.getJobId())
            .orElseThrow(NotFoundJobException::new);
    }
}

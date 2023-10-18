package com.example.wanted.application;

import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobRepository;
import com.example.wanted.domain.resume.ResumeRepository;
import com.example.wanted.domain.user.User;
import com.example.wanted.domain.user.UserRepository;
import com.example.wanted.dto.resume.ResumeRequest;
import com.example.wanted.exception.apply.AlreadyResumeSubmittedException;
import com.example.wanted.exception.job.JobNotFoundException;
import com.example.wanted.exception.user.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResumeService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;

    public ResumeService(JobRepository jobRepository, UserRepository userRepository, ResumeRepository resumeRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.resumeRepository = resumeRepository;
    }

    @Transactional
    public void applyJob(ResumeRequest resumeRequest) {
        User user = findUser(resumeRequest);
        Job job = findJob(resumeRequest);
        if (isUserJobApplied(user, job)) {
            throw new AlreadyResumeSubmittedException();
        }
        resumeRepository.save(resumeRequest
            .toResumeWithUserAndJob(user, job));
    }

    private boolean isUserJobApplied(User user, Job job) {
        return resumeRepository.existsByUserAndJob(user, job);
    }

    private User findUser(ResumeRequest resumeRequest) {
        return userRepository.findById(resumeRequest.getUserId())
            .orElseThrow(UserNotFoundException::new);
    }

    private Job findJob(ResumeRequest resumeRequest) {
        return jobRepository.findById(resumeRequest.getJobId())
            .orElseThrow(JobNotFoundException::new);
    }
}

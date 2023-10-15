package com.example.wanted.domain.user;

import com.example.wanted.domain.BaseEntity;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.job.JobApplication;
import com.example.wanted.exception.ApplicationAlreadyExistsException;
import com.example.wanted.exception.user.UserNameNullException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;


@Table(name = "USERS")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<JobApplication> jobApplications;

    @Builder
    private User(final Long id, final String name) {
        validateNullName(name);
        this.name = name;
        this.id = id;
    }

    private void validateNullName(String name) {
        if (Objects.isNull(name) || name.trim().isEmpty()) {
            throw new UserNameNullException();
        }
    }

    public void applyJob(Job job) {
        if (isAppliedForJob(job)) {
            throw new ApplicationAlreadyExistsException();
        }
        JobApplication jobApplication = new JobApplication(this, job);
        jobApplications.add(jobApplication);
    }

    public boolean isAppliedForJob(Job job) {
        return this.jobApplications.stream()
            .anyMatch(it -> it.getJob().getId() == job.getId());
    }
}

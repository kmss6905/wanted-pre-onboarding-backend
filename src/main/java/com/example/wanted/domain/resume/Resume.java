package com.example.wanted.domain.resume;

import com.example.wanted.domain.BaseEntity;
import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Resume extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @Builder
    private Resume(final User user, final Job job) {
        assert user != null;
        assert job != null;
        this.user = user;
        this.job = job;
    }

    public boolean matchesJob(Job job) {
        return Objects.equals(this.job.getId(), job.getId());
    }

    public boolean matchJobId(long jobId) {
        return Objects.equals(this.job.getId(), jobId);
    }
}
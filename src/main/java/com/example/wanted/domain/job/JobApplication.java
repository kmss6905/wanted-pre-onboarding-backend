package com.example.wanted.domain.job;

import com.example.wanted.domain.BaseEntity;
import com.example.wanted.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "JOB_APPLICATION")
public class JobApplication extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private Job job;

    public JobApplication(final User user, final Job job) {
        assert user != null;
        assert job != null;
        this.user = user;
        this.job = job;
    }
}
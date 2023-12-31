package com.example.wanted.domain.job;

import com.example.wanted.domain.BaseEntity;
import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.resume.Resume;
import com.example.wanted.exception.job.InvalidJobContentException;
import com.example.wanted.exception.job.UpdateJobNullException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Job extends BaseEntity {

    @Column(name = "position")
    private String position;

    @Column(name = "money")
    private long money;

    @Column(name = "description")
    private String description;

    @Column(name = "tech")
    private String tech;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    private Job(final Long id, final String position, final long money, final String description, final String tech, final Company company) {
        verifyNotBlank(position, description, tech);
        this.position = position;
        this.money = money;
        this.description = description;
        this.tech = tech;
        this.company = company;
        this.id = id;
    }

    private void verifyNotBlank(String position, String description, String tech) {
        if (Objects.isNull(position) || position.isBlank()) {
            throw new InvalidJobContentException();
        }

        if (Objects.isNull(description) || description.isBlank()) {
            throw new InvalidJobContentException();
        }

        if (Objects.isNull(tech) || tech.isBlank()) {
            throw new InvalidJobContentException();
        }
    }

    public void update(Job job) {
        validateJobNotNull(job);
        this.position = job.position;
        this.money = job.money;
        this.description = job.description;
        this.tech = job.tech;
    }

    private void validateJobNotNull(Job job) {
        if (Objects.isNull(job)) {
            throw new UpdateJobNullException();
        }
    }
}

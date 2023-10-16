package com.example.wanted.domain.job;

import com.example.wanted.domain.BaseEntity;
import com.example.wanted.domain.company.Company;
import com.example.wanted.exception.job.CompanyNullException;
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

    private String position;
    private long money;
    private String description;
    private String tech;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Company company;

    @Builder
    private Job(final Long id, final String position, final long money, final String description, final String tech, final Company company) {
        this.position = position;
        this.money = money;
        this.description = description;
        this.tech = tech;
        this.company = company;
        this.id = id;
    }

//    private void validateCompanyNotNull(Company company) {
//        if (Objects.isNull(company)) {
//            throw new CompanyNullException();
//        }
//    }

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

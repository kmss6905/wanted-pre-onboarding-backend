package com.example.wanted.domain.company;

import com.example.wanted.domain.BaseEntity;
import com.example.wanted.domain.job.Job;
import com.example.wanted.exception.company.CompanyCountryNullException;
import com.example.wanted.exception.company.CompanyNameNullException;
import com.example.wanted.exception.company.CompanyCityNullException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseEntity {

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "country", columnDefinition = "VARCHAR(255)")
    private String country;

    @Column(name = "nation", columnDefinition = "VARCHAR(255)")
    private String city;

    @OneToMany(mappedBy = "company")
    private List<Job> jobs;

    @Builder
    private Company(final Long id, final String name, final String country, final String city) {
        validateNull(name, country, city);
        this.name = name;
        this.country = country;
        this.city = city;
        this.id = id;
    }

    private void validateNull(String name, String country, String nation) {
        if (Objects.isNull(name) || name.trim().isEmpty()) {
            throw new CompanyNameNullException();
        }

        if (Objects.isNull(country) || country.trim().isEmpty()) {
            throw new CompanyCountryNullException();
        }

        if (Objects.isNull(nation) || nation.trim().isEmpty()) {
            throw new CompanyCityNullException();
        }
    }

    public Long getId() {
        return this.id;
    }
}

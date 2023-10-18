package com.example.wanted.domain.resume;

import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query(value = "select r from Resume r where r.user =:user and r.job =:job")
    Optional<Resume> findByUserAndJob(@Param("user") User user, @Param("job") Job jOb);

    boolean existsByUserAndJob(@Param("user") User user, @Param("job") Job jOb);
}

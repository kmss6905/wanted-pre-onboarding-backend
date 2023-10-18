package com.example.wanted.domain.job;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT e FROM Job e WHERE e.tech LIKE %:searchTerm% OR e.description LIKE %:searchTerm% OR e.position LIKE %:searchTerm% ORDER BY e.id DESC")
    Page<Job> findJobsBySearchCondition(@Param("searchTerm") String searchTerm, Pageable pageable);
}

package com.example.wanted.ui;

import com.example.wanted.application.JobSearchService;
import com.example.wanted.application.JobService;
import com.example.wanted.dto.job.AddJobRequest;
import com.example.wanted.dto.job.JobResponse;
import com.example.wanted.dto.job.UpdateJobRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;
    private final JobSearchService searchService;

    public JobController(JobService jobService, JobSearchService searchService) {
        this.jobService = jobService;
        this.searchService = searchService;
    }

    @PostMapping
    public ResponseEntity<Void> createJob(
        @Valid @RequestBody AddJobRequest addJobRequest) {
        jobService.addJob(addJobRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{jobId}")
    public ResponseEntity<Void> updateJob(
        @PathVariable long jobId,
        @Valid @RequestBody UpdateJobRequest updateJobRequest) {
        jobService.updateJob(jobId, updateJobRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> findJob(@PathVariable long jobId) {
        return ResponseEntity.ok().body(searchService.findOneJob(jobId));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> findJobs(
        @RequestParam("search") String text,
        Pageable pageable) {
        return ResponseEntity.ok(searchService.searchJobsWithPaging(text, pageable));
    }
}

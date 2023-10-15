package com.example.wanted.ui;

import com.example.wanted.application.JobService;
import com.example.wanted.dto.job.AddJobRequest;
import com.example.wanted.dto.job.JobResponse;
import com.example.wanted.dto.job.UpdateJobRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Void> createJob(
        @Valid @RequestBody AddJobRequest addJobRequest)
    {
        jobService.addJob(addJobRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{jobId}")
    public ResponseEntity<Void> updateJob(
        @PathVariable long jobId,
        @Valid @RequestBody UpdateJobRequest updateJobRequest)
    {
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
        return ResponseEntity.ok().body(jobService.findOneJob(jobId));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> findJobs(Pageable pageable) {
        return ResponseEntity.ok(jobService.findJobs(pageable));
    }
}

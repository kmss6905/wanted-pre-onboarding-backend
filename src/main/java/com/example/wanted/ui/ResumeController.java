package com.example.wanted.ui;

import com.example.wanted.application.ResumeService;
import com.example.wanted.domain.user.User;
import com.example.wanted.domain.user.UserRepository;
import com.example.wanted.dto.resume.ResumeRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/apply")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<Void> applyJob(
        @Valid @RequestBody ResumeRequest resumeRequest
    ) {
        resumeService.applyJob(resumeRequest);
        return ResponseEntity.noContent().build();
    }
}

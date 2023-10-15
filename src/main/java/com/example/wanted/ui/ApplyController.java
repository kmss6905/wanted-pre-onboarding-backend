package com.example.wanted.ui;

import com.example.wanted.application.ApplyService;
import com.example.wanted.dto.apply.ApplyJobRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/apply")
public class ApplyController {

    private final ApplyService applyService;

    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @PostMapping
    public ResponseEntity<Void> applyJob(
        @Valid @RequestBody ApplyJobRequest applyJobRequest
    ) {
        applyService.applyJob(applyJobRequest);
        return ResponseEntity.noContent().build();
    }
}

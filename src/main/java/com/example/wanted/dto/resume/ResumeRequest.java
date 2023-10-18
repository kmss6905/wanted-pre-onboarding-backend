package com.example.wanted.dto.resume;

import com.example.wanted.domain.job.Job;
import com.example.wanted.domain.resume.Resume;
import com.example.wanted.domain.user.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResumeRequest {
    @NotNull
    @Min(value = 1, message = "최소 {value} 이어야 합니다.")
    private Long jobId;

    @NotNull
    @Min(value = 1, message = "최소 {value} 이어야 합니다.")
    private Long userId;
    public Resume toResumeWithUserAndJob(User user, Job job) {
        return Resume.builder()
            .user(user)
            .job(job)
            .build();
    }
}

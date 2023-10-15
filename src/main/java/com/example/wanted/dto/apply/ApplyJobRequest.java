package com.example.wanted.dto.apply;

import com.example.wanted.domain.job.JobApplication;
import lombok.Getter;

@Getter
public class ApplyJobRequest {
    private long jobId;
    private long userId;

}

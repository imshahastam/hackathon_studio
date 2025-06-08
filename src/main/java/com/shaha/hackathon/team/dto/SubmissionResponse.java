package com.shaha.hackathon.team.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubmissionResponse {
    private Long id;
    private String frontendRepo;
    private String backendRepo;
    private String comment;
    private LocalDateTime submittedAt;
}


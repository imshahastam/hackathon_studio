package com.shaha.hackathon.team.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HackathonSubmissionDTO {
    private Long teamId;
    private Long submissionId;
    private String teamName;
    private String frontendRepo;
    private String backendRepo;
    private String comment;
    private LocalDateTime submittedAt;
}

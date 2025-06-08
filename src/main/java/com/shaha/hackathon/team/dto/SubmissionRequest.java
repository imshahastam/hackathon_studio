package com.shaha.hackathon.team.dto;

import lombok.Data;

@Data
public class SubmissionRequest {
    private String frontendRepo;
    private String backendRepo;
    private String comment;
}

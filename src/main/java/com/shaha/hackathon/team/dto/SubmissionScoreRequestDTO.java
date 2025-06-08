package com.shaha.hackathon.team.dto;

import lombok.Data;

import java.util.Map;

@Data
public class SubmissionScoreRequestDTO {
    private Map<Long, Integer> scores; // key: criterionId, value: score
    private String comment;
}

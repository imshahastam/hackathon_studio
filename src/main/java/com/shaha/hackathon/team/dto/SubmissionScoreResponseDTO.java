package com.shaha.hackathon.team.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubmissionScoreResponseDTO {
    private Long submissionId;
    private String teamName;
    private List<ScoreItem> scores;
    private String scoreComment;

    @Data
    public static class ScoreItem {
        private Long criterionId;
        private String criterionName;
        private String judgeName;
        private Integer score;
    }
}


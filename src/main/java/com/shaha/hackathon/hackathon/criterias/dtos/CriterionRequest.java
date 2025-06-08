package com.shaha.hackathon.hackathon.criterias.dtos;

import lombok.Data;

@Data
public class CriterionRequest {
    private String name;
    private String description;
    private int maxScore;
}

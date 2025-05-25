package com.shaha.hackathon.judge.models.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class JudgeRegistrationDTO {
    private String company;
    private Integer workExperience;
    private String bio;
    private String linkedin;
    private List<Long> tagsId;
    private List<String> newTags;
}

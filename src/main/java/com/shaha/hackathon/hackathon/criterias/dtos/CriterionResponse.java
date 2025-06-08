package com.shaha.hackathon.hackathon.criterias.dtos;

import com.shaha.hackathon.hackathon.criterias.model.Criterion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CriterionResponse {
    Long id;
    Long hackathonId;
    String name;
    Integer maxScore;
    String description;

    public CriterionResponse(Criterion criterion) {
        this.id = criterion.getId();
        this.hackathonId = criterion.getHackathon().getId();
        this.name = criterion.getName();
        this.maxScore = criterion.getMaxScore();
        this.description = criterion.getDescription();
    }
}

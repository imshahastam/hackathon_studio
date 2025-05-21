package com.shaha.hackathon.judge.models.dto;

import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.judge.models.Judge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class JudgeCardDTO {
    Long id;
    Long userId;
    String username;
    String firstName;
    String lastName;
    String company;
    Integer workExperience;
    List<String> competences;

    public JudgeCardDTO(Judge judge) {
        this.id = judge.getId();
        this.userId = judge.getUser().getId();
        this.username = judge.getUser().getUsername();
        this.firstName = judge.getUser().getFirstName();
        this.lastName = judge.getUser().getLastName();
        this.company = judge.getCompany();
        this.workExperience = judge.getWorkExperience();
        this.competences = judge.getCompetences().stream()
                .map(Competence::getName)
                .toList();
    }
}

package com.shaha.hackathon.judge.models.dto;

import com.shaha.hackathon.judge.models.Judge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class JudgeDTO {
    Long id;
    Long userId;
    String username;
    String firstName;
    String lastName;
    String company;
    Integer workExperience;
    String bio;
    String linkedin;

    public JudgeDTO(Judge judge) {
        this.id = judge.getId();
        this.userId = judge.getUser().getId();
        this.username = judge.getUser().getUsername();
        this.firstName = judge.getUser().getFirstName();
        this.lastName = judge.getUser().getLastName();
        this.company = judge.getCompany();
        this.workExperience = judge.getWorkExperience();
        this.bio = judge.getBio();
        this.linkedin = judge.getLinkedin();
    }

}

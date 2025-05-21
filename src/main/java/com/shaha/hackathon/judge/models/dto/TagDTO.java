package com.shaha.hackathon.judge.models.dto;

import com.shaha.hackathon.judge.models.Competence;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class TagDTO {
    Long id;
    String name;

    public TagDTO(Competence competence) {
        this.id = competence.getId();
        this.name = competence.getName();
    }
}

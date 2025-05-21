package com.shaha.hackathon.hackathon.model.dto;

import com.shaha.hackathon.hackathon.model.Hackathon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class HackathonIdDTO {
    Long id;

    public HackathonIdDTO(Hackathon hackathon) {
        this.id = hackathon.getId();
    }
}

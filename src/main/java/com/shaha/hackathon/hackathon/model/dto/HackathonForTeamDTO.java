package com.shaha.hackathon.hackathon.model.dto;

import com.shaha.hackathon.hackathon.model.Hackathon;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonForTeamDTO {
    Long id;
    String name;
    String organizerName;

    public HackathonForTeamDTO(Hackathon hackathon) {
        this.id = hackathon.getId();
        this.name = hackathon.getName();
        this.organizerName = hackathon.getOrganizer().getFullName();
    }
}

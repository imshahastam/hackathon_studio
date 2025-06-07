package com.shaha.hackathon.hackathon.model.dto;

import com.shaha.hackathon.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonParticipantDTO {
    Long id;
    String username;
    String fullName;
    String teamName;

    public HackathonParticipantDTO(User user, String teamName) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.teamName = teamName;
    }
}

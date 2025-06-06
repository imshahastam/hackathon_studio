package com.shaha.hackathon.team.dto;

import com.shaha.hackathon.hackathon.model.dto.HackathonForTeamDTO;
import com.shaha.hackathon.team.Team;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamInfoDTO {
    CreateTeamDTO teamInfo;
    HackathonForTeamDTO hackathonInfo;

    public TeamInfoDTO(Team team) {
        this.teamInfo = new CreateTeamDTO(team);
        this.hackathonInfo = new HackathonForTeamDTO(team.getHackathon());
    }
}

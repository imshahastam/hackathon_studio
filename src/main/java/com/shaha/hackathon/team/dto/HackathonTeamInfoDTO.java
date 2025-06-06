package com.shaha.hackathon.team.dto;

import com.shaha.hackathon.team.Team;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonTeamInfoDTO {
    Long id;
    String name;
    String leaderFullName;
    Integer membersAmount;

    public HackathonTeamInfoDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.leaderFullName = team.getLeader().getFullName();
        this.membersAmount = team.getMembers().size();
    }
}

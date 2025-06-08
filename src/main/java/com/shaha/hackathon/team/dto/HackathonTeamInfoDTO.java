package com.shaha.hackathon.team.dto;

import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonTeamInfoDTO {
    Long id;
    String name;
    Long leaderId;
    String leaderFullName;
    Integer membersAmount;
    List<Long> memberIds;

    public HackathonTeamInfoDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.leaderId = team.getLeader().getId();
        this.leaderFullName = team.getLeader().getFullName();
        this.membersAmount = team.getMembers().size();
        this.memberIds = team.getMembers().stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
}

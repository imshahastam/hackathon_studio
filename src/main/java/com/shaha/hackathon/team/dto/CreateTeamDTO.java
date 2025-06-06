package com.shaha.hackathon.team.dto;

import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.user.dto.UserInfoDTO;
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
public class CreateTeamDTO {
    Long id;
    String name;
    UserInfoDTO leaderInfo;
    List<UserInfoDTO> membersInfo;

    public CreateTeamDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.leaderInfo = new UserInfoDTO(
                team.getLeader().getId(),
                team.getLeader().getUsername(),
                team.getLeader().getFirstName(),
                team.getLeader().getLastName()
        );
        this.membersInfo = team.getMembers().stream()
                .map(UserInfoDTO::from)
                .collect(Collectors.toList());
    }

}

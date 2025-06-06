package com.shaha.hackathon.team.services;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.TeamRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.team.dto.CreateTeamDTO;
import com.shaha.hackathon.team.dto.HackathonTeamInfoDTO;
import com.shaha.hackathon.team.dto.TeamInfoDTO;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;
    private final UserService userService;

    public CreateTeamDTO createTeam(Long hackathonId, String teamName) {
        User user = userService.getCurrentUser();
        Hackathon hackathon = hackathonRepository.findById(Math.toIntExact(hackathonId))
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));

        if (!user.getHackathons().contains(hackathon)) {
            throw new RuntimeException("User is not approved for this hackathon");
        }

        // Проверка: не в команде ли уже
        boolean alreadyInTeam = teamRepository.existsByHackathonAndMembersContains(hackathon, user);
        if (alreadyInTeam) {
            throw new RuntimeException("User already in a team for this hackathon");
        }

        Team team = new Team();
        team.setName(teamName);
        team.setLeader(user);
        team.setHackathon(hackathon);
        team.getMembers().add(user);
        teamRepository.save(team);

        return new CreateTeamDTO(team);
    }

    public List<HackathonTeamInfoDTO> getTeamsByHackathon(Long hackathonId) {
        List<Team> teams = teamRepository.findByHackathonId(hackathonId);
        return teams.stream()
                .map(team -> new HackathonTeamInfoDTO(
                        team.getId(),
                        team.getName(),
                        team.getLeader().getFullName(), // Предполагается, что метод getFullName() уже есть
                        team.getMembers().size()
                ))
                .collect(Collectors.toList());
    }

    public ResponseEntity<TeamInfoDTO> getTeam(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        if (team.isPresent()) {
            return ResponseEntity.ok(new TeamInfoDTO(team.get()));
        }
        return ResponseEntity.notFound().build();
    }

    public void joinTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        User user = userService.getCurrentUser();

        if (!user.getHackathons().contains(team.getHackathon())) {
            throw new RuntimeException("User is not approved for this hackathon");
        }

        if (team.getMembers().contains(user)) {
            throw new RuntimeException("User already in this team");
        }

        // Проверка: не в другой команде ли уже
        boolean alreadyInTeam = teamRepository.existsByHackathonAndMembersContains(team.getHackathon(), user);
        if (alreadyInTeam) {
            throw new RuntimeException("User already in another team");
        }

        team.getMembers().add(user);
        teamRepository.save(team);
    }

    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        Long leaderId = userService.getCurrentUser().getId();
        if (!team.getLeader().getId().equals(leaderId)) {
            throw new RuntimeException("Only the team leader can delete the team");
        }

        // Удалим только команду, участники сами отвяжутся
        team.getMembers().clear();
        teamRepository.delete(team);
    }
}


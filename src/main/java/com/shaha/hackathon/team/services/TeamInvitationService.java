package com.shaha.hackathon.team.services;

import com.shaha.hackathon.judge.models.InvitationStatus;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.TeamInvitationRepository;
import com.shaha.hackathon.repo.TeamRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.team.TeamInvitation;
import com.shaha.hackathon.team.dto.CreateTeamDTO;
import com.shaha.hackathon.team.dto.HackathonTeamInfoDTO;
import com.shaha.hackathon.team.dto.TeamInvitationDTO;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamInvitationService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;
    private final TeamInvitationRepository invitationRepository;
    private final UserService userService;

    public void inviteToTeam(Long teamId, String username) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        User leader = userService.getCurrentUser();

        if (!team.getLeader().equals(leader)) {
            throw new RuntimeException("Only team leader can invite members");
        }

        User invited = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!invited.getHackathons().contains(team.getHackathon())) {
            throw new RuntimeException("User not approved for this hackathon");
        }

        if (team.getMembers().contains(invited)) {
            throw new RuntimeException("User already in this team");
        }

        boolean alreadyInTeam = teamRepository.existsByHackathonAndMembersContains(team.getHackathon(), invited);
        if (alreadyInTeam) {
            throw new RuntimeException("User already in another team");
        }

        invitationRepository.save(new TeamInvitation(null, team, invited, InvitationStatus.PENDING, LocalDateTime.now()));
    }

    public void respondToInvitation(Long invitationId, boolean accept) {
        TeamInvitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));

        Long userId = userService.getCurrentUser().getId();
        if (!invitation.getInvited().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized action");
        }

        if (accept) {
            Team team = invitation.getTeam();
            if (!team.getMembers().contains(invitation.getInvited())) {
                team.getMembers().add(invitation.getInvited());
                teamRepository.save(team);
            }
            invitation.setStatus(InvitationStatus.ACCEPTED);
        } else {
            invitation.setStatus(InvitationStatus.DECLINED);
        }

        invitationRepository.save(invitation);
    }

    public List<TeamInvitationDTO> getUserPendingInvites() {
        Long userId = userService.getCurrentUser().getId();
        List<TeamInvitation> teamInvitations = invitationRepository.findByInvitedIdAndStatus(userId, InvitationStatus.PENDING);
        return teamInvitations.stream()
                .map(inv -> new TeamInvitationDTO(
                        inv.getId(),
                        new CreateTeamDTO(inv.getTeam()),
                        inv.getSentAt()
                ))
                .collect(Collectors.toList());
    }
}

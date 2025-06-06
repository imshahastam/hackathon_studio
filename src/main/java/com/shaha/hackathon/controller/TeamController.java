package com.shaha.hackathon.controller;

import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.team.TeamInvitation;
import com.shaha.hackathon.team.dto.CreateTeamDTO;
import com.shaha.hackathon.team.dto.HackathonTeamInfoDTO;
import com.shaha.hackathon.team.dto.TeamInfoDTO;
import com.shaha.hackathon.team.dto.TeamInvitationDTO;
import com.shaha.hackathon.team.services.TeamInvitationService;
import com.shaha.hackathon.team.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamInvitationService teamInvitationService;

    @PostMapping("/create")
    public ResponseEntity<CreateTeamDTO> createTeam(@RequestParam Long hackathonId,
                                                    @RequestParam String name) {
        return ResponseEntity.ok(teamService.createTeam(hackathonId, name));
    }

    @PostMapping("/{teamId}/join")
    public ResponseEntity<String> joinTeam(@PathVariable Long teamId) {
        teamService.joinTeam(teamId);
        return ResponseEntity.ok("Joined team successfully");
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<List<HackathonTeamInfoDTO>> getTeams(@PathVariable Long hackathonId) {
        return ResponseEntity.ok(teamService.getTeamsByHackathon(hackathonId));
    }

    @PostMapping("/{teamId}/invite")
    public ResponseEntity<String> inviteToTeam(@PathVariable Long teamId,
                                               @RequestParam String username) {
        teamInvitationService.inviteToTeam(teamId, username);
        return ResponseEntity.ok("Invitation sent");
    }

    @PostMapping("/invitations/{invitationId}/respond")
    public ResponseEntity<String> respondToInvitation(@PathVariable Long invitationId,
                                                      @RequestParam boolean accept) {
        teamInvitationService.respondToInvitation(invitationId, accept);
        return ResponseEntity.ok("Invitation " + (accept ? "accepted" : "rejected"));
    }

    @PostMapping("/{teamId}/leave")
    public ResponseEntity<String> leaveTeam(@PathVariable Long teamId) {
        teamInvitationService.leaveTeam(teamId);
        return ResponseEntity.ok("You left the team");
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.ok("Team deleted");
    }

    @GetMapping("/invitations")
    public ResponseEntity<List<TeamInvitationDTO>> getMyInvitations() {
        return ResponseEntity.ok(teamInvitationService.getUserPendingInvites());
    }

    //get team's details
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamInfoDTO> getTeamById(@PathVariable Long teamId) {
        return teamService.getTeam(teamId);
    }
}


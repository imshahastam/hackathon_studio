package com.shaha.hackathon.controller;

import com.shaha.hackathon.exceptions.MessageResponse;
import com.shaha.hackathon.hackathon.model.dto.HackathonCardDTO;
import com.shaha.hackathon.judge.models.InvitationStatus;
import com.shaha.hackathon.judge.models.JudgeInvitation;
import com.shaha.hackathon.judge.models.dto.JudgeCardDTO;
import com.shaha.hackathon.judge.models.dto.JudgeInvitationDTO;
import com.shaha.hackathon.judge.models.dto.JudgeInvitationRequestDTO;
import com.shaha.hackathon.judge.models.dto.RespondToInvitationRequest;
import com.shaha.hackathon.judge.services.InvitationService;
import com.shaha.hackathon.judge.services.JudgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/judges")
public class JudgeController {
    private final InvitationService invitationService;
    private final JudgeService judgeService;

    public JudgeController(InvitationService invitationService,
                           JudgeService judgeService) {
        this.invitationService = invitationService;
        this.judgeService = judgeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<JudgeCardDTO>> getJudges() {
        return judgeService.getAllJudges();
    }

    @GetMapping("/me/invitations")
    public ResponseEntity<List<JudgeInvitationRequestDTO>> getAllJudgeInvitations() {
        return invitationService.getAllJudgeInvitations();
    }

    @PostMapping("/invitations/{invitationId}/respond")
    public ResponseEntity<MessageResponse> respondToInvitation(@PathVariable Long invitationId, @RequestBody RespondToInvitationRequest respond) {
        return invitationService.respondToInvitation(invitationId, respond);
    }

    @GetMapping("/{hackathonId}/invitations")
    public ResponseEntity<List<JudgeInvitationDTO>> getInvitationsByHackathon(@PathVariable Long hackathonId) {
        List<JudgeInvitation> invitations = invitationService.getInvitationsByHackathonId(hackathonId);
        List<JudgeInvitationDTO> dtos = invitations.stream()
                .map(inv -> new JudgeInvitationDTO(inv.getJudgeId(), inv.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

}

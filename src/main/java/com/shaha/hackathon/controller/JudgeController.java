package com.shaha.hackathon.controller;

import com.shaha.hackathon.exceptions.MessageResponse;
import com.shaha.hackathon.judge.models.InvitationStatus;
import com.shaha.hackathon.judge.models.JudgeInvitation;
import com.shaha.hackathon.judge.models.dto.RespondToInvitationRequest;
import com.shaha.hackathon.judge.services.InvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/judges")
public class JudgeController {
    private final InvitationService invitationService;

    public JudgeController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

//    @GetMapping("/me/invitations")
//    public ResponseEntity<List<JudgeInvitation>> getAllMyInvitations() {
//
//    }

    @PostMapping("/invitations/{invitationId}/respond")
    public ResponseEntity<MessageResponse> respondToInvitation(@PathVariable Long invitationId, @RequestBody RespondToInvitationRequest respond) {
        return invitationService.respondToInvitation(invitationId, respond);
    }
}

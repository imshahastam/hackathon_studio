package com.shaha.hackathon.controller;

import com.shaha.hackathon.exceptions.MessageResponse;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.hackathon.model.dto.*;
import com.shaha.hackathon.hackathon.service.*;
import com.shaha.hackathon.judge.models.dto.InviteJudgeRequest;
import com.shaha.hackathon.judge.models.dto.JudgeDTO;
import com.shaha.hackathon.judge.services.InvitationService;
import com.shaha.hackathon.user.dto.UserInfoDTO;
import com.shaha.hackathon.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/hackathons")
@RequiredArgsConstructor
public class HackathonController {
    private final GetHackathonsService getHackathonsService;
    private final CreateHackathonService createHackathonService;
    private final UpdateHackathonService updateHackathonService;
    private final GetHackathonService getHackathonService;
    private final GetMyHackathonsService getMyHackathonsService;
    private final DeleteHackathonService deleteHackathonService;
    private final GetHackathonsJudgesService getHackathonsJudgesService;
    private final InvitationService invitationService;
    private final UserService userService;

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping("/create")
    public ResponseEntity<HackathonCardDTO> createHackathon(@Valid @RequestBody HackathonCreateDTO hackathonDTO) {
        return createHackathonService.execute(hackathonDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HackathonCardDTO>> getHackathons() {
        return getHackathonsService.execute(null);
    }

    @PutMapping("/{hackathonId}/edit")
    public ResponseEntity<HackathonIdDTO> updateHackathon(@PathVariable Long hackathonId, @Valid @RequestBody UpdateHackathonCommand dto) {
        return updateHackathonService.execute(hackathonId, dto);
    }

    @GetMapping("/{hackathonId}")
    public ResponseEntity<HackathonDTO> getHackathonById(@PathVariable Long hackathonId) {
        return getHackathonService.execute(hackathonId);
    }

    @GetMapping("/my")
    public ResponseEntity<List<HackathonCardDTO>> getHackathonsByOrganizerId() {
        return getMyHackathonsService.execute(null);
    }

    @DeleteMapping("/{hackathonId}")
    public ResponseEntity<Void> deleteHackathonById(@PathVariable Long hackathonId) {
        return deleteHackathonService.execute(hackathonId);
    }

    @GetMapping("/{hackathonId}/hack-judges")
    public ResponseEntity<List<JudgeDTO>> getHackathonsJudges(@PathVariable Long hackathonId) {
        return getHackathonsJudgesService.execute(hackathonId);
    }

    @GetMapping("/{hackathonId}/participants")
    public ResponseEntity<List<HackathonParticipantDTO>> getParticipantsByHackathon(@PathVariable Long hackathonId) {
        return userService.getParticipantsByHackathonId(hackathonId);
    }

    @PostMapping("/{hackathonId}/invite-judge")
    public ResponseEntity<MessageResponse> inviteJudge(
            @PathVariable Long hackathonId,
            @RequestBody InviteJudgeRequest request
    ) {
        invitationService.sendInvitation(hackathonId, request.getJudgeId());

        return ResponseEntity.ok(new MessageResponse("Invitation sent."));
    }
}

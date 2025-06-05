package com.shaha.hackathon.controller;

import com.shaha.hackathon.exceptions.MessageResponse;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.hackathon.model.dto.HackathonIdDTO;
import com.shaha.hackathon.hackathon.model.dto.UpdateHackathonCommand;
import com.shaha.hackathon.hackathon.model.dto.HackathonCardDTO;
import com.shaha.hackathon.hackathon.model.dto.HackathonCreateDTO;
import com.shaha.hackathon.hackathon.service.*;
import com.shaha.hackathon.judge.models.dto.InviteJudgeRequest;
import com.shaha.hackathon.judge.models.dto.JudgeDTO;
import com.shaha.hackathon.judge.services.InvitationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/hackathons")
public class HackathonController {
    private final GetHackathonsService getHackathonsService;
    private final CreateHackathonService createHackathonService;
    private final RegisterUserToHackathonService registerUserToHackathonService;
    private final UpdateHackathonService updateHackathonService;
    private final GetHackathonService getHackathonService;
    private final GetMyHackathonsService getMyHackathonsService;
    private final DeleteHackathonService deleteHackathonService;
    private final GetHackathonsJudgesService getHackathonsJudgesService;
    private final InvitationService invitationService;

    public HackathonController(GetHackathonsService getHackathonsService,
                               CreateHackathonService createHackathonService,
                               RegisterUserToHackathonService registerUserToHackathonService,
                               UpdateHackathonService updateHackathonService,
                               GetHackathonService getHackathonService,
                               GetMyHackathonsService getMyHackathonsService,
                               DeleteHackathonService deleteHackathonService,
                               GetHackathonsJudgesService getHackathonsJudgesService,
                               InvitationService invitationService) {
        this.getHackathonsService = getHackathonsService;
        this.createHackathonService = createHackathonService;
        this.registerUserToHackathonService = registerUserToHackathonService;
        this.updateHackathonService = updateHackathonService;
        this.getHackathonService = getHackathonService;
        this.getMyHackathonsService = getMyHackathonsService;
        this.deleteHackathonService = deleteHackathonService;
        this.getHackathonsJudgesService = getHackathonsJudgesService;
        this.invitationService = invitationService;
    }

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

    @PostMapping("/{hackathonId}/invite-judge")
    public ResponseEntity<MessageResponse> inviteJudge(
            @PathVariable Long hackathonId,
            @RequestBody InviteJudgeRequest request
    ) {
        invitationService.sendInvitation(hackathonId, request.getJudgeId());

        return ResponseEntity.ok(new MessageResponse("Invitation sent."));
    }

//    @DeleteMapping
//    public ResponseEntity<Void> deleteJudgeFromHackathon() {
//        return ResponseEntity.ok();
//    }
}

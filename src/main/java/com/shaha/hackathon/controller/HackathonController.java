package com.shaha.hackathon.controller;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.hackathon.model.UpdateHackathonCommand;
import com.shaha.hackathon.hackathon.service.*;
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

    public HackathonController(GetHackathonsService getHackathonsService,
                               CreateHackathonService createHackathonService,
                               RegisterUserToHackathonService registerUserToHackathonService,
                               UpdateHackathonService updateHackathonService,
                               GetHackathonService getHackathonService,
                               GetMyHackathonsService getMyHackathonsService) {
        this.getHackathonsService = getHackathonsService;
        this.createHackathonService = createHackathonService;
        this.registerUserToHackathonService = registerUserToHackathonService;
        this.updateHackathonService = updateHackathonService;
        this.getHackathonService = getHackathonService;
        this.getMyHackathonsService = getMyHackathonsService;
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping("/create")
    public ResponseEntity<HackathonDTO> createHackathon(@Valid @RequestBody Hackathon hackathon) {
        return createHackathonService.execute(hackathon);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HackathonDTO>> getHackathons() {
        return getHackathonsService.execute(null);
    }

    @PutMapping("/{hackathonId}")
    public ResponseEntity<HackathonDTO> updateHackathon(@PathVariable Long hackathonId, @Valid @RequestBody Hackathon hackathon) {
        return updateHackathonService.execute(new UpdateHackathonCommand(hackathonId, hackathon));
    }

    @GetMapping("/{hackathonId}")
    public ResponseEntity<HackathonDTO> updateHackathon(@PathVariable Long hackathonId) {
        return getHackathonService.execute(hackathonId);
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PostMapping("/{hackathonId}/register/{userId}")
    public ResponseEntity<String> registerUserToHackathon(@PathVariable Long hackathonId, @PathVariable Long userId) {
        return registerUserToHackathonService.register(userId, hackathonId);
    }

    @GetMapping("/my")
    public ResponseEntity<List<HackathonDTO>> getHackathonsByOrganizerId() {
        return getMyHackathonsService.execute(null);
    }
}

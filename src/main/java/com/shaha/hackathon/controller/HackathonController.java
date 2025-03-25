package com.shaha.hackathon.controller;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.hackathon.model.UpdateHackathonCommand;
import com.shaha.hackathon.hackathon.service.CreateHackathonService;
import com.shaha.hackathon.hackathon.service.GetHackathonsService;
import com.shaha.hackathon.hackathon.service.RegisterUserToHackathonService;
import com.shaha.hackathon.hackathon.service.UpdateHackathonService;
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

    public HackathonController(GetHackathonsService getHackathonsService,
                               CreateHackathonService createHackathonService,
                               RegisterUserToHackathonService registerUserToHackathonService, UpdateHackathonService updateHackathonService) {
        this.getHackathonsService = getHackathonsService;
        this.createHackathonService = createHackathonService;
        this.registerUserToHackathonService = registerUserToHackathonService;
        this.updateHackathonService = updateHackathonService;
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping("/create")
    public ResponseEntity<HackathonDTO> createHackathon(@RequestBody Hackathon hackathon) {
        return createHackathonService.execute(hackathon);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HackathonDTO>> getHackathons() {
        return getHackathonsService.execute(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HackathonDTO> updateHackathon(@PathVariable Long id, @RequestBody Hackathon hackathon) {
        return updateHackathonService.execute(new UpdateHackathonCommand(id, hackathon));
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PostMapping("/{hackathonId}/register/{userId}")
    public ResponseEntity<String> registerUserToHackathon(@PathVariable Long hackathonId, @PathVariable Long userId) {
        return registerUserToHackathonService.register(userId, hackathonId);
    }
}

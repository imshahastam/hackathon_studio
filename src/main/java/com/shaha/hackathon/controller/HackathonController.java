package com.shaha.hackathon.controller;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.hackathon.service.CreateHackathonService;
import com.shaha.hackathon.hackathon.service.GetHackathonsService;
import com.shaha.hackathon.hackathon.service.RegisterUserToHackathonService;
import com.shaha.hackathon.repo.HackathonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hackathons")
public class HackathonController {
    private final GetHackathonsService getHackathonsService;
    private final CreateHackathonService createHackathonService;
    private final RegisterUserToHackathonService registerUserToHackathonService;

    public HackathonController(GetHackathonsService getHackathonsService,
                               CreateHackathonService createHackathonService,
                               RegisterUserToHackathonService registerUserToHackathonService) {
        this.getHackathonsService = getHackathonsService;
        this.createHackathonService = createHackathonService;
        this.registerUserToHackathonService = registerUserToHackathonService;
    }

    @PostMapping("/create")
    public ResponseEntity<HackathonDTO> createHackathon(@RequestBody Hackathon hackathon) {
        return createHackathonService.execute(hackathon);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HackathonDTO>> getHackathons() {
        return getHackathonsService.execute(null);
    }

    @PostMapping("/{hackathonId}/register/{userId}")
    public ResponseEntity<String> registerUserToHackathon(@PathVariable Long hackathonId, @PathVariable Long userId) {
        return registerUserToHackathonService.register(userId, hackathonId);
    }
}

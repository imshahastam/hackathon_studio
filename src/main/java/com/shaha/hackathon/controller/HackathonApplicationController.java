package com.shaha.hackathon.controller;

import com.shaha.hackathon.hackathon.model.dto.ApplicationDTO;
import com.shaha.hackathon.hackathon.service.HackathonApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/hackathons")
public class HackathonApplicationController {

    private final HackathonApplicationService applicationService;

    public HackathonApplicationController(HackathonApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/{hackathonId}/apply")
    public ResponseEntity<?> apply(@PathVariable Long hackathonId, @RequestParam Long userId) {
        applicationService.apply(userId, hackathonId);
        return ResponseEntity.ok("Application sent");
    }

    @GetMapping("/{hackathonId}/applied")
    public ResponseEntity<Boolean> hasUserApplied(
            @PathVariable Long hackathonId,
            @RequestParam Long userId
    ) {
        boolean applied = applicationService.hasUserApplied(userId, hackathonId);
        return ResponseEntity.ok(applied);
    }

    @GetMapping("/{hackathonId}/applications")
    public List<ApplicationDTO> getApplications(@PathVariable Long hackathonId) {
        return applicationService.getPendingApplications(hackathonId)
                .stream()
                .map(app -> new ApplicationDTO(app.getUser().getId(),
                        app.getUser().getFirstName(),
                        app.getUser().getLastName()))
                .toList();
    }

    @PostMapping("/{hackathonId}/applications/{userId}/approve")
    public ResponseEntity<?> approve(@PathVariable Long hackathonId, @PathVariable Long userId) {
        applicationService.approve(userId, hackathonId);
        return ResponseEntity.ok("User approved");
    }

    @PostMapping("/{hackathonId}/applications/{userId}/decline")
    public ResponseEntity<?> decline(@PathVariable Long hackathonId, @PathVariable Long userId) {
        applicationService.decline(userId, hackathonId);
        return ResponseEntity.ok("User declined");
    }
}


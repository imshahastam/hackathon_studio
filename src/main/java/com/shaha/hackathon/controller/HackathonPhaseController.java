package com.shaha.hackathon.controller;

import com.shaha.hackathon.hackathon.model.dto.HackathonIdDTO;
import com.shaha.hackathon.hackathon.model.dto.HackathonPhaseDTO;
import com.shaha.hackathon.hackathon.model.dto.UpdateHackathonCommand;
import com.shaha.hackathon.hackathon.service.HackathonPhaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/hackathons/{hackathonId}/phases")
@RequiredArgsConstructor
public class HackathonPhaseController {
    private final HackathonPhaseService phaseService;

    @PostMapping
    public ResponseEntity<?> addPhases(@PathVariable Long hackathonId,
                                       @RequestBody List<HackathonPhaseDTO> dtos) {
        phaseService.addPhases(hackathonId, dtos);
        return ResponseEntity.ok(Map.of("message", "Phases added"));
    }

    @GetMapping
    public ResponseEntity<List<HackathonPhaseDTO>> getTimeline(@PathVariable Long hackathonId) {
        return ResponseEntity.ok(phaseService.getTimeline(hackathonId));
    }

    @DeleteMapping("/{phaseId}")
    public ResponseEntity<Void> deletePhaseById(@PathVariable Long hackathonId, @PathVariable Long phaseId) {
        return phaseService.deletePhase(hackathonId, phaseId);
    }

    @PutMapping
    public ResponseEntity<?> updatePhases(@PathVariable Long hackathonId,
                                          @RequestBody List<HackathonPhaseDTO> dtos) {
        phaseService.updatePhases(hackathonId, dtos);
        return ResponseEntity.ok(Map.of("message", "Phases updated"));
    }
}

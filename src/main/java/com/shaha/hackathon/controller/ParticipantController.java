package com.shaha.hackathon.controller;

import com.shaha.hackathon.hackathon.model.dto.HackathonCardDTO;
import com.shaha.hackathon.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {
    private final UserService userService;

    @GetMapping("/my-hackathons")
    public ResponseEntity<List<HackathonCardDTO>> getHackathonsByParticipantId() {
        return ResponseEntity.ok().body(userService.getHackathonsByUserId());
    }

}

package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Query;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.dto.HackathonCardDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMyHackathonsService implements Query<Void, List<HackathonCardDTO>> {
    private final HackathonRepository hackathonRepository;
    private final UserService userService;

    public GetMyHackathonsService(HackathonRepository hackathonRepository, UserService userService) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<HackathonCardDTO>> execute(Void input) {
        Long currUserId = userService.getCurrentUser().getId();
        List<Hackathon> hackathons = hackathonRepository.findByOrganizerId(currUserId);
        List<HackathonCardDTO> hackathonDTOS = hackathons.stream().map(HackathonCardDTO::new).toList();

        return ResponseEntity.status(HttpStatus.OK).body(hackathonDTOS);
    }
}

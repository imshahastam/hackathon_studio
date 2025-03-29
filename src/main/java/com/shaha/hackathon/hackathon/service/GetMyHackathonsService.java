package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Query;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMyHackathonsService implements Query<Void, List<HackathonDTO>> {
    private final HackathonRepository hackathonRepository;
    private final UserService userService;

    public GetMyHackathonsService(HackathonRepository hackathonRepository, UserService userService) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<HackathonDTO>> execute(Void input) {
        Long currUserId = userService.getCurrentUser().getId();
        List<Hackathon> hackathons = hackathonRepository.findByOrganizerId(currUserId);
        List<HackathonDTO> hackathonDTOS = hackathons.stream().map(HackathonDTO::new).toList();

        return ResponseEntity.status(HttpStatus.OK).body(hackathonDTOS);
    }
}

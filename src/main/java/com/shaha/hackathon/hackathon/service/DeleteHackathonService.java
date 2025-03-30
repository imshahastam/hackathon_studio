package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Command;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteHackathonService implements Command<Long, Void> {
    private final HackathonRepository hackathonRepository;
    private final UserService userService;

    public DeleteHackathonService(HackathonRepository hackathonRepository, UserService userService) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> execute(Long hackathonId) {
        Optional<Hackathon> hackathonOptional = hackathonRepository.findById(Math.toIntExact(hackathonId));
        if (hackathonOptional.isPresent()) {
            User organizer = userService.getCurrentUser();
            User savedOrg = hackathonOptional.get().getOrganizer();
            if (organizer == savedOrg) {
                hackathonRepository.deleteById(Math.toIntExact(hackathonId));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                throw new RuntimeException("U can't delete not your hackathon");
            }
        }
        //throw new ProductNotFoundException();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

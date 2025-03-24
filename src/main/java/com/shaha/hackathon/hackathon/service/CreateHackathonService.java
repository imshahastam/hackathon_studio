package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Command;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateHackathonService implements Command<Hackathon, HackathonDTO> {
    private final HackathonRepository hackathonRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public CreateHackathonService(HackathonRepository hackathonRepository,
                                  UserRepository userRepository,
                                  UserService userService) {
        this.hackathonRepository = hackathonRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<HackathonDTO> execute(Hackathon hackathon) {
        User organizer = userService.getCurrentUser();
        hackathon.setOrganizer(organizer); // Устанавливаем текущего пользователя как организатора

        //ProductValidator.execute(hackathon);

        Hackathon savedHackathon = hackathonRepository.save(hackathon);
        System.out.println("saved Hack: " + savedHackathon);
        return ResponseEntity.status(HttpStatus.CREATED).body(new HackathonDTO(savedHackathon));
    }
}

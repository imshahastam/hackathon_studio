package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Command;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateHackathonService implements Command<Hackathon, HackathonDTO> {
    private final HackathonRepository hackathonRepository;
    private final UserRepository userRepository;

    public CreateHackathonService(HackathonRepository hackathonRepository,
                                  UserRepository userRepository) {
        this.hackathonRepository = hackathonRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<HackathonDTO> execute(Hackathon hackathon) {
        Optional<User> organizerOptional = userRepository.findById(hackathon.getOrganizer().getId());
        if (organizerOptional.isPresent()) {
            hackathon.setOrganizer(organizerOptional.get());
        }
        System.out.println(hackathon);

        //ProductValidator.execute(hackathon);

        Hackathon savedHackathon = hackathonRepository.save(hackathon);
        System.out.println("saved Hack: " + savedHackathon);
        return ResponseEntity.status(HttpStatus.CREATED).body(new HackathonDTO(savedHackathon));
    }
}

package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Query;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterUserToHackathonService {
    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;

    public RegisterUserToHackathonService(UserRepository userRepository,
                                          HackathonRepository hackathonRepository) {
        this.userRepository = userRepository;
        this.hackathonRepository = hackathonRepository;
    }

    public ResponseEntity<String> register(Long userId, Long hackathonId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Hackathon hackathon = hackathonRepository.findById(Math.toIntExact(hackathonId)).orElseThrow(() -> new RuntimeException("Hackathon not found"));

        // Проверяем, что у пользователя есть роль "УЧАСТНИК"
        boolean isParticipant = user.getRoles().stream().anyMatch(role -> role.getNameOfRole().equals("PARTICIPANT"));
        if (!isParticipant) {
            throw new RuntimeException("User does not have PARTICIPANT role");
        }

        // Добавляем пользователя в хакатон
        user.getHackathons().add(hackathon);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User successfully assigned to hackathon");
    }
}

package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Command;
import com.shaha.hackathon.exceptions.AccessDeniedException;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.hackathon.model.dto.UpdateHackathonCommand;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.repo.CompetenceRepository;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UpdateHackathonService {
    private final HackathonRepository hackathonRepository;
    private final UserService userService;
    private final CompetenceRepository competenceRepository;

    public UpdateHackathonService(HackathonRepository hackathonRepository,
                                  UserService userService,
                                  CompetenceRepository competenceRepository) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
        this.competenceRepository = competenceRepository;
    }

    public ResponseEntity<HackathonDTO> execute(Long id, UpdateHackathonCommand dto) {

        Hackathon hackathon = hackathonRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));

        User currentUser = userService.getCurrentUser();
        if (!hackathon.getOrganizer().equals(currentUser)) {
            throw new AccessDeniedException("Access Denied");
        }

        hackathon.setName(dto.getName());
        hackathon.setDescription(dto.getDescription());
        hackathon.setStatus(dto.getStatus());
        hackathon.setType(dto.getType());
        hackathon.setStartDate(dto.getStartDate());
        hackathon.setEndDate(dto.getEndDate());
        hackathon.setLocation(dto.getLocation());
        hackathon.setPrizeFund(dto.getPrizeFund());
        hackathon.setConditions(dto.getConditions());

        // получаем компетенции по ID
        List<Competence> competenceList = competenceRepository.findAllById(dto.getTags());
        hackathon.setTags(new HashSet<>(competenceList));

        hackathon.onUpdate();
        hackathonRepository.save(hackathon);

        return ResponseEntity.ok(new HackathonDTO(hackathon));
    }
}

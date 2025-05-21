package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Command;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.dto.HackathonCardDTO;
import com.shaha.hackathon.hackathon.model.dto.HackathonCreateDTO;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.repo.CompetenceRepository;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CreateHackathonService implements Command<HackathonCreateDTO, HackathonCardDTO> {
    private final HackathonRepository hackathonRepository;
    private final UserService userService;
    private final CompetenceRepository competenceRepository;

    public CreateHackathonService(HackathonRepository hackathonRepository,
                                  UserService userService,
                                  CompetenceRepository competenceRepository) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
        this.competenceRepository = competenceRepository;
    }

    @Override
    public ResponseEntity<HackathonCardDTO> execute(HackathonCreateDTO hackathonDTO) {
        User organizer = userService.getCurrentUser();

        Hackathon hackathon = new Hackathon();
        hackathon.setName(hackathonDTO.getName());
        hackathon.setDescription(hackathonDTO.getDescription());
        hackathon.setStatus(hackathonDTO.getStatus());
        hackathon.setType(hackathonDTO.getType());
        hackathon.setStartDate(hackathonDTO.getStartDate());
        hackathon.setEndDate(hackathonDTO.getEndDate());
        hackathon.setLocation(hackathonDTO.getLocation());
        hackathon.setPrizeFund(hackathonDTO.getPrizeFund());
        hackathon.setConditions(hackathonDTO.getConditions());
        hackathon.setOrganizer(organizer);

        Set<Competence> allTags = new HashSet<>();

        // 1. Добавляем существующие тэги
        if (hackathonDTO.getTagsId() != null && !hackathonDTO.getTagsId().isEmpty()) {
            List<Competence> existingTags = competenceRepository.findAllById(hackathonDTO.getTagsId());
            allTags.addAll(existingTags);
        }

        // 2. Добавляем новые тэги
        if (hackathonDTO.getNewTags() != null && !hackathonDTO.getNewTags().isEmpty()) {
            for (String tagName : hackathonDTO.getNewTags()) {
                // Проверка на дубликат в базе (можно убрать, если точно уникальные)
                boolean exists = competenceRepository.existsByName(tagName);
                if (!exists) {
                    Competence newTag = new Competence();
                    newTag.setName(tagName);
                    Competence saved = competenceRepository.save(newTag);
                    allTags.add(saved);
                } else {
                    // если тэг с таким именем есть — добавим его
                    competenceRepository.findByName(tagName).ifPresent(allTags::add);
                }
            }
        }

        hackathon.setTags(allTags);

        Hackathon saved = hackathonRepository.save(hackathon);
        return ResponseEntity.status(HttpStatus.CREATED).body(new HackathonCardDTO(saved));
    }
}

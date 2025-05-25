package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Command;
import com.shaha.hackathon.exceptions.AccessDeniedException;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.hackathon.model.dto.HackathonIdDTO;
import com.shaha.hackathon.hackathon.model.dto.UpdateHackathonCommand;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.judge.services.SaveNewAndExistingTagsService;
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
import java.util.Set;

@Slf4j
@Service
public class UpdateHackathonService {
    private final HackathonRepository hackathonRepository;
    private final UserService userService;
    private final CompetenceRepository competenceRepository;
    private final SaveNewAndExistingTagsService tagsResolveService;

    public UpdateHackathonService(HackathonRepository hackathonRepository,
                                  UserService userService,
                                  CompetenceRepository competenceRepository,
                                  SaveNewAndExistingTagsService tagsResolveService) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
        this.competenceRepository = competenceRepository;
        this.tagsResolveService = tagsResolveService;
    }

    public ResponseEntity<HackathonIdDTO> execute(Long id, UpdateHackathonCommand dto) {

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

        Set<Competence> allTags = new HashSet<>();

        if (dto.getTagsId() != null && !dto.getTagsId().isEmpty()) {
            List<Competence> existingTags = competenceRepository.findAllById(dto.getTagsId());
            allTags.addAll(existingTags);
        }

        if (dto.getNewTags() != null && !dto.getNewTags().isEmpty()) {
            for (String tagName : dto.getNewTags()) {
                boolean exists = competenceRepository.existsByName(tagName);
                if (!exists) {
                    Competence newTag = new Competence();
                    newTag.setName(tagName);
                    Competence saved = competenceRepository.save(newTag);
                    allTags.add(saved);
                } else {
                    competenceRepository.findByName(tagName).ifPresent(allTags::add);
                }
            }
        }

        hackathon.setTags(allTags);
        hackathon.onUpdate();
        hackathonRepository.save(hackathon);

        return ResponseEntity.ok(new HackathonIdDTO(hackathon));
    }
}

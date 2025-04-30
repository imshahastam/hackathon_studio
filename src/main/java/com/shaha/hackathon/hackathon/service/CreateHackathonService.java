package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Command;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.hackathon.model.dto.HackathonCreateDTO;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.repo.CompetenceRepository;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CreateHackathonService implements Command<HackathonCreateDTO, HackathonDTO> {
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
    public ResponseEntity<HackathonDTO> execute(HackathonCreateDTO hackathonDTO) {
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

        // получаем компетенции по ID
        List<Competence> competenceList = competenceRepository.findAllById(hackathonDTO.getTagsId());
        hackathon.setTags(new HashSet<>(competenceList));

        Hackathon saved = hackathonRepository.save(hackathon);
        return ResponseEntity.status(HttpStatus.CREATED).body(new HackathonDTO(saved));
    }
}

package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Command;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.hackathon.model.UpdateHackathonCommand;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UpdateHackathonService implements Command<UpdateHackathonCommand, HackathonDTO> {
    private final HackathonRepository hackathonRepository;
    private final UserService userService;

    public UpdateHackathonService(HackathonRepository hackathonRepository,
                                  UserService userService) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<HackathonDTO> execute(UpdateHackathonCommand command) {

        Optional<Hackathon> hackathonOptional = hackathonRepository.findById(Math.toIntExact(command.getId()));
        if (hackathonOptional.isPresent()) {
            Hackathon hackathon = command.getHackathon();
            hackathon.setId(command.getId());
            hackathon.onUpdate();
            User organizer = userService.getCurrentUser();
            User savedOrg = hackathonOptional.get().getOrganizer();
            log.info("Current user: {}", organizer);
            log.info("Before user: {}", savedOrg);
            if (organizer == savedOrg) {
                hackathon.setOrganizer(organizer);
            } else {
                throw new RuntimeException("Different user");
            }

            //ProductValidator.execute(hackathon);
            hackathonRepository.save(hackathon);

            return ResponseEntity.ok(new HackathonDTO(hackathon));
        }
        return ResponseEntity.badRequest().body(new HackathonDTO(command.getHackathon()));
        //throw new ProductNotFoundException();
    }
}

package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Query;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.dto.HackathonCardDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetHackathonsService implements Query<Void, List<HackathonCardDTO>> {
    private final HackathonRepository hackathonRepository;

    public GetHackathonsService(HackathonRepository hackathonRepository) {
        this.hackathonRepository = hackathonRepository;
    }

    @Override
    public ResponseEntity<List<HackathonCardDTO>> execute(Void input) {
        List<Hackathon> hackathons = hackathonRepository.findAll();
        List<HackathonCardDTO> hackathonDTOS = hackathons.stream().map(HackathonCardDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(hackathonDTOS);
    }
}

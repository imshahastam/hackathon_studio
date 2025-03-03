package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Query;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetHackathonsService implements Query<Void, List<HackathonDTO>> {
    private final HackathonRepository hackathonRepository;

    public GetHackathonsService(HackathonRepository hackathonRepository) {
        this.hackathonRepository = hackathonRepository;
    }

    @Override
    public ResponseEntity<List<HackathonDTO>> execute(Void input) {
        List<Hackathon> hackathons = hackathonRepository.findAll();
        List<HackathonDTO> hackathonDTOS = hackathons.stream().map(HackathonDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(hackathonDTOS);
    }
}

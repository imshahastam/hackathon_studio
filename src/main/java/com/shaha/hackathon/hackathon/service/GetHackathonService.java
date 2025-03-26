package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Query;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetHackathonService implements Query<Long, HackathonDTO> {
    private final HackathonRepository hackathonRepository;

    public GetHackathonService(HackathonRepository hackathonRepository) {
        this.hackathonRepository = hackathonRepository;
    }

    @Override
    public ResponseEntity<HackathonDTO> execute(Long hackathonId) {

        Optional<Hackathon> optionalHackathon = hackathonRepository.findById(Math.toIntExact(hackathonId));
        if (optionalHackathon.isPresent()) {
            return ResponseEntity.ok(new HackathonDTO(optionalHackathon.get()));
        }
        //throw new ProductNotFoundException();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

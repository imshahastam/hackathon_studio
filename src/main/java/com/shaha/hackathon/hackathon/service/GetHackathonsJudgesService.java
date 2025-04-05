package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.Query;
import com.shaha.hackathon.judge.models.Judge;
import com.shaha.hackathon.judge.models.dto.JudgeDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.JudgeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetHackathonsJudgesService implements Query<Long, List<JudgeDTO>> {
    private final JudgeRepository judgeRepository;
    private final HackathonRepository hackathonRepository;

    public GetHackathonsJudgesService(JudgeRepository judgeRepository, HackathonRepository hackathonRepository) {
        this.judgeRepository = judgeRepository;
        this.hackathonRepository = hackathonRepository;
    }

    @Override
    public ResponseEntity<List<JudgeDTO>> execute(Long hackathonId) {
        if (!hackathonRepository.existsById(Math.toIntExact(hackathonId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Judge> judges = judgeRepository.findByHackathonId(hackathonId);
        List<JudgeDTO> judgeDTOS = judges.stream().map(JudgeDTO::new).toList();
        return ResponseEntity.ok(judgeDTOS);
    }
}

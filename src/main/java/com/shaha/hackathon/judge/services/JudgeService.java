package com.shaha.hackathon.judge.services;

import com.shaha.hackathon.exceptions.AlreadyInvitedException;
import com.shaha.hackathon.exceptions.MessageResponse;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.dto.HackathonCardDTO;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.judge.models.Judge;
import com.shaha.hackathon.judge.models.dto.JudgeCardDTO;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.JudgeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeService {
    private final HackathonRepository hackathonRepository;
    private final JudgeRepository judgeRepository;

    public JudgeService(HackathonRepository hackathonRepository, JudgeRepository judgeRepository) {
        this.hackathonRepository = hackathonRepository;
        this.judgeRepository = judgeRepository;
    }

    public ResponseEntity<MessageResponse> addJudgeToHackathon(Long hackathonId, Long judgeId) {
        Hackathon hackathon = hackathonRepository.findById(Math.toIntExact(hackathonId))
                .orElseThrow(() -> new RuntimeException("Хакатон не найден"));

        Judge judge = judgeRepository.findById(judgeId)
                .orElseThrow(() -> new RuntimeException("Судья не найден"));

        // Проверка, не добавлен ли уже
        if (hackathon.getJudges().contains(judge)) {
            throw new AlreadyInvitedException("Судья уже привязан к хакатону.");
        }

        hackathon.getJudges().add(judge);
        hackathonRepository.save(hackathon);
        return ResponseEntity.ok(new MessageResponse("Judge added to Hackathon"));
    }

    public ResponseEntity<List<JudgeCardDTO>> getAllJudges() {
        List<Judge> judges = judgeRepository.findAll();
        List<JudgeCardDTO> judgeCardDTOS = judges.stream().map(JudgeCardDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(judgeCardDTOS);
    }
}

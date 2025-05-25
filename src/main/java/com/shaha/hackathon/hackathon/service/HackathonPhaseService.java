package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonPhase;
import com.shaha.hackathon.hackathon.model.dto.HackathonPhaseDTO;
import com.shaha.hackathon.repo.HackathonPhaseRepository;
import com.shaha.hackathon.repo.HackathonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HackathonPhaseService {
    private final HackathonRepository hackathonRepository;
    private final HackathonPhaseRepository phaseRepository;

    public void addPhases(Long hackathonId, List<HackathonPhaseDTO> dtos) {
        Hackathon hackathon = hackathonRepository.findById(Math.toIntExact(hackathonId))
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));

        List<HackathonPhase> phases = dtos.stream().map(dto -> {
            HackathonPhase phase = new HackathonPhase();
            phase.setHackathon(hackathon);
            phase.setName(dto.getName());
            phase.setDescription(dto.getDescription());
            phase.setType(dto.getType());
            phase.setStartTime(dto.getStartTime());
            phase.setEndTime(dto.getEndTime());
            phase.setSystem(dto.isSystem());
            return phase;
        }).toList();

        phaseRepository.saveAll(phases);
    }

    public List<HackathonPhaseDTO> getTimeline(Long hackathonId) {
        return phaseRepository.findByHackathonId(hackathonId).stream().map(phase ->
                new HackathonPhaseDTO(
                        phase.getId(),
                        phase.getName(),
                        phase.getDescription(),
                        phase.getType(),
                        phase.getStartTime(),
                        phase.getEndTime(),
                        phase.isSystem()
                )
        ).toList();
    }
}

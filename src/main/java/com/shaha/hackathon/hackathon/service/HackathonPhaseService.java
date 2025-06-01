package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonPhase;
import com.shaha.hackathon.hackathon.model.dto.HackathonPhaseDTO;
import com.shaha.hackathon.repo.HackathonPhaseRepository;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public ResponseEntity<Void> deletePhase(Long hackathonId, Long phaseId) {
        Optional<HackathonPhase> phaseOptional = phaseRepository.findByHackathonId(hackathonId).stream()
                .filter(hackathonPhase -> hackathonPhase.getId().equals(phaseId))
                .findFirst();
        if (phaseOptional.isPresent()) {
                phaseRepository.deleteById(phaseId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Transactional
    public void updatePhases(Long hackathonId, List<HackathonPhaseDTO> dtos) {
        Hackathon hackathon = hackathonRepository.findById(Math.toIntExact(hackathonId))
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));

        // Соберём id всех фаз, которые пришли от фронта (существующие)
        Set<Long> idsFromFrontend = dtos.stream()
                .filter(dto -> dto.getId() != null)
                .map(HackathonPhaseDTO::getId)
                .collect(Collectors.toSet());

        // Найдём все существующие фазы в базе для этого хакатона
        List<HackathonPhase> phasesInDb = phaseRepository.findByHackathonId(hackathonId);

        // 1. Удаляем те, которых нет во входных данных (на фронте их удалили)
        for (HackathonPhase phase : phasesInDb) {
            if (!idsFromFrontend.contains(phase.getId())) {
                phaseRepository.delete(phase);
            }
        }

        // 2. Обновляем существующие и добавляем новые
        for (HackathonPhaseDTO dto : dtos) {
            if (dto.getId() != null) {
                // Update existing phase
                HackathonPhase phase = phaseRepository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Phase not found: " + dto.getId()));
                phase.setName(dto.getName());
                phase.setDescription(dto.getDescription());
                phase.setType(dto.getType());
                phase.setStartTime(dto.getStartTime());
                phase.setEndTime(dto.getEndTime());
                phase.setSystem(dto.isSystem());
                phaseRepository.save(phase);
            } else {
                // Create new phase
                HackathonPhase phase = new HackathonPhase();
                phase.setHackathon(hackathon);
                phase.setName(dto.getName());
                phase.setDescription(dto.getDescription());
                phase.setType(dto.getType());
                phase.setStartTime(dto.getStartTime());
                phase.setEndTime(dto.getEndTime());
                phase.setSystem(dto.isSystem());
                phaseRepository.save(phase);
            }
        }
    }

}

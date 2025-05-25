package com.shaha.hackathon.repo;

import com.shaha.hackathon.hackathon.model.HackathonPhase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HackathonPhaseRepository extends JpaRepository<HackathonPhase, Long> {
    List<HackathonPhase> findByHackathonId(Long hackathonId);
}

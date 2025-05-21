package com.shaha.hackathon.repo;

import com.shaha.hackathon.judge.models.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    boolean existsByName(String tagName);

    Optional<Competence> findByName(String tagName);
}

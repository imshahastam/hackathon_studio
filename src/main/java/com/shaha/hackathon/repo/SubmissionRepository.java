package com.shaha.hackathon.repo;

import com.shaha.hackathon.team.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByTeamId(Long teamId);
}


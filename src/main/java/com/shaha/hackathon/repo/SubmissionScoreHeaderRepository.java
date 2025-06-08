package com.shaha.hackathon.repo;

import com.shaha.hackathon.team.SubmissionScoreHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionScoreHeaderRepository extends JpaRepository<SubmissionScoreHeader, Long> {
    void deleteBySubmissionIdAndJudgeId(Long submissionId, Long id);

    List<SubmissionScoreHeader> findBySubmissionId(Long submissionId);
}

package com.shaha.hackathon.repo;

import com.shaha.hackathon.team.SubmissionScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionScoreRepository extends JpaRepository<SubmissionScore, Long> {
    List<SubmissionScore> findByScoreHeader_Submission_Id(Long submissionId);
    List<SubmissionScore> findByScoreHeader_Judge_IdAndScoreHeader_Submission_Id(Long judgeId, Long submissionId);
}
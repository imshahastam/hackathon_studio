package com.shaha.hackathon.repo;

import com.shaha.hackathon.judge.models.JudgeInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JudgeInvitationRepository extends JpaRepository<JudgeInvitation, Long> {
    boolean existsByHackathonIdAndJudgeId(Long hackathonId, Long judgeId);
    List<JudgeInvitation> findByJudgeId(Long judgeId);

    List<JudgeInvitation> findByHackathonId(Long hackathonId);
}

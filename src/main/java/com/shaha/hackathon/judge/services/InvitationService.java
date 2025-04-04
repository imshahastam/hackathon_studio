package com.shaha.hackathon.judge.services;

import com.shaha.hackathon.exceptions.AlreadyInvitedException;
import com.shaha.hackathon.judge.models.JudgeInvitation;
import com.shaha.hackathon.repo.JudgeInvitationRepository;
import org.springframework.stereotype.Service;

@Service
public class InvitationService {
    private final JudgeInvitationRepository invitationRepository;

    public InvitationService(JudgeInvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    public void sendInvitation(Long hackathonId, Long judgeId) {
        boolean alreadyInvited = invitationRepository.existsByHackathonIdAndJudgeId(hackathonId, judgeId);

        if (alreadyInvited) {
            throw new AlreadyInvitedException("Судья уже приглашён на этот хакатон.");
        }

        JudgeInvitation invitation = new JudgeInvitation(hackathonId, judgeId);
        invitationRepository.save(invitation);

        // Здесь можно вызвать notificationService, emailService и т.п.
    }
}

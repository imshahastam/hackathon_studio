package com.shaha.hackathon.judge.services;

import com.shaha.hackathon.exceptions.AccessDeniedException;
import com.shaha.hackathon.exceptions.AlreadyInvitedException;
import com.shaha.hackathon.exceptions.MessageResponse;
import com.shaha.hackathon.judge.models.InvitationStatus;
import com.shaha.hackathon.judge.models.JudgeInvitation;
import com.shaha.hackathon.judge.models.dto.RespondToInvitationRequest;
import com.shaha.hackathon.repo.JudgeInvitationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InvitationService {
    private final JudgeInvitationRepository invitationRepository;
    private final JudgeService judgeService;

    public InvitationService(JudgeInvitationRepository invitationRepository, JudgeService judgeService) {
        this.invitationRepository = invitationRepository;
        this.judgeService = judgeService;
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

    public ResponseEntity<MessageResponse> respondToInvitation(Long invitationId, RespondToInvitationRequest respond) {
        JudgeInvitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Приглашение не найдено"));

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("На приглашение уже был дан ответ.");
        }

        invitation.setStatus(respond.getAction());
        invitationRepository.save(invitation);

        if (respond.getAction() == InvitationStatus.ACCEPTED) {
            return judgeService.addJudgeToHackathon(invitation.getHackathonId(), invitation.getJudgeId());
        }
        return ResponseEntity.ok(new MessageResponse("Invitation have been DECLINED"));
    }

}

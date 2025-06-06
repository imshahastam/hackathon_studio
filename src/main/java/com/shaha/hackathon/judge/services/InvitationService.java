package com.shaha.hackathon.judge.services;

import com.shaha.hackathon.exceptions.AlreadyInvitedException;
import com.shaha.hackathon.exceptions.MessageResponse;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.dto.HackathonForJudgeInvitationDTO;
import com.shaha.hackathon.judge.models.InvitationStatus;
import com.shaha.hackathon.judge.models.Judge;
import com.shaha.hackathon.judge.models.JudgeInvitation;
import com.shaha.hackathon.judge.models.dto.JudgeInvitationRequestDTO;
import com.shaha.hackathon.judge.models.dto.RespondToInvitationRequest;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.JudgeInvitationRepository;
import com.shaha.hackathon.repo.JudgeRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvitationService {
    private final JudgeInvitationRepository invitationRepository;
    private final JudgeService judgeService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JudgeRepository judgeRepository;
    private final HackathonRepository hackathonRepository;

    public InvitationService(JudgeInvitationRepository invitationRepository,
                             JudgeService judgeService,
                             UserRepository userRepository,
                             UserService userService,
                             JudgeRepository judgeRepository,
                             HackathonRepository hackathonRepository) {
        this.invitationRepository = invitationRepository;
        this.judgeService = judgeService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.judgeRepository = judgeRepository;
        this.hackathonRepository = hackathonRepository;
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

    public ResponseEntity<List<JudgeInvitationRequestDTO>> getAllJudgeInvitations() {
        Long currentUserId = userService.getCurrentUser().getId();
        Judge judge = judgeRepository.findByUserId(currentUserId);
        List<JudgeInvitationRequestDTO> invitations = invitationRepository.findByJudgeId(judge.getId())
                .stream()
                .map(invitation -> new JudgeInvitationRequestDTO(
                        invitation.getId(),
                        invitation.getHackathonId(),
                        mapHackathonToDTO(invitation.getHackathonId()), // Предположим, у вас есть этот метод
                        invitation.getJudgeId(),
                        invitation.getStatus(),
                        invitation.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(invitations);
    }

    public List<JudgeInvitation> getInvitationsByHackathonId(Long hackathonId) {
        return invitationRepository.findByHackathonId(hackathonId);
    }

    private HackathonForJudgeInvitationDTO mapHackathonToDTO(Long hackathonId) {
        Hackathon hackathon = hackathonRepository.findById(Math.toIntExact(hackathonId))
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));

        return new HackathonForJudgeInvitationDTO(
                hackathon.getName(),
                hackathon.getType(),
                hackathon.getStartDate(),
                hackathon.getLocation()
        );
    }


}

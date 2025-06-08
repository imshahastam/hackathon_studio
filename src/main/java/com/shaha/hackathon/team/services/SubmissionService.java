package com.shaha.hackathon.team.services;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.judge.models.Judge;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.SubmissionRepository;
import com.shaha.hackathon.repo.TeamRepository;
import com.shaha.hackathon.team.Submission;
import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.team.dto.HackathonSubmissionDTO;
import com.shaha.hackathon.team.dto.SubmissionRequest;
import com.shaha.hackathon.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final TeamRepository teamRepository;
    private final HackathonRepository hackathonRepository;
    private final UserService userService;

    public Submission submitWork(Long teamId, SubmissionRequest request) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow (() -> new RuntimeException("Team not found"));

        Long userId = userService.getCurrentUser().getId();
        if (!team.getLeader().getId().equals(userId)) {
            throw new RuntimeException("Only team leader can submit the work.");
        }

        // если повторная отправка — обновляем существующую
        Submission submission = submissionRepository.findByTeamId(teamId)
                .orElse(new Submission());

        submission.setTeam(team);
        submission.setFrontendRepo(request.getFrontendRepo());
        submission.setBackendRepo(request.getBackendRepo());
        submission.setComment(request.getComment());
        submission.setSubmittedAt(LocalDateTime.now());

        return submissionRepository.save(submission);
    }

    public Submission getByTeam(Long teamId) {
        return submissionRepository.findByTeamId(teamId)
                .orElseThrow(() -> new RuntimeException("No submission found for this team"));
    }

    public List<HackathonSubmissionDTO> getHackathonSubmissions(Long hackathonId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));

        Long currentUserId = userService.getCurrentUser().getId();

        boolean isOrganizer = hackathon.getOrganizer().getId().equals(currentUserId);

        // Проверка: если не организатор, проверяем — назначен ли как судья
        boolean isJudge = hackathon.getJudges().stream()
                .anyMatch(judge -> judge.getUser().getId().equals(currentUserId));

        if (!isOrganizer && !isJudge) {
            throw new RuntimeException("Access denied: you are not authorized to view submissions");
        }

        // Сборка DTO
        List<Team> teams = teamRepository.findByHackathonId(hackathonId);

        return teams.stream()
                .map(team -> {
                    Optional<Submission> optionalSubmission = submissionRepository.findByTeamId(team.getId());
                    if (optionalSubmission.isEmpty()) return null;

                    Submission submission = optionalSubmission.get();
                    HackathonSubmissionDTO dto = new HackathonSubmissionDTO();
                    dto.setTeamId(team.getId());
                    dto.setSubmissionId(submission.getId());
                    dto.setTeamName(team.getName());
                    dto.setFrontendRepo(submission.getFrontendRepo());
                    dto.setBackendRepo(submission.getBackendRepo());
                    dto.setComment(submission.getComment());
                    dto.setSubmittedAt(submission.getSubmittedAt());
                    return dto;
                })
                .filter(Objects::nonNull)
                .toList();
    }


}


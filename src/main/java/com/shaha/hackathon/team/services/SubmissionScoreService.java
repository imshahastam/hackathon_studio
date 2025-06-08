package com.shaha.hackathon.team.services;

import com.shaha.hackathon.exceptions.AccessDeniedException;
import com.shaha.hackathon.hackathon.criterias.model.Criterion;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.judge.models.Judge;
import com.shaha.hackathon.repo.*;
import com.shaha.hackathon.team.Submission;
import com.shaha.hackathon.team.SubmissionScore;
import com.shaha.hackathon.team.SubmissionScoreHeader;
import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.team.dto.SubmissionScoreResponseDTO;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubmissionScoreService {
    private final SubmissionScoreRepository scoreRepository;
    private final SubmissionRepository submissionRepository;
    private final SubmissionScoreHeaderRepository scoreHeaderRepository;
    private final JudgeRepository judgeRepository;
    private final CriterionRepository criterionRepository;
    private final TeamRepository teamRepository;
    private final UserService userService;

    public void submitScores(Long submissionId, Map<Long, Integer> scores, String comment) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        Long currentUserId = userService.getCurrentUser().getId();
        Judge judge = judgeRepository.findByUserId(currentUserId);

        if (judge == null) {
            throw new RuntimeException("You are not a judge");
        }

        Hackathon hackathon = submission.getTeam().getHackathon();
        if (!hackathon.getJudges().contains(judge)) {
            throw new RuntimeException("You are not assigned as a judge for this hackathon");
        }

        // Удалим старый header если существует (перезапись)
        scoreHeaderRepository.deleteBySubmissionIdAndJudgeId(submissionId, judge.getId());

        SubmissionScoreHeader header = new SubmissionScoreHeader();
        header.setSubmission(submission);
        header.setJudge(judge);
        header.setComment(comment);
        List<SubmissionScore> scoreList = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : scores.entrySet()) {
            Criterion criterion = criterionRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Criterion not found"));

            SubmissionScore score = new SubmissionScore();
            score.setCriterion(criterion);
            score.setScore(entry.getValue());
            score.setScoreHeader(header);
            scoreList.add(score);
        }

        header.setScores(scoreList);
        scoreHeaderRepository.save(header);
    }

    public SubmissionScoreResponseDTO getSubmissionScores(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        // 🔐 Проверка прав доступа
        User currentUser = userService.getCurrentUser();
        boolean isTeamMember = teamRepository.existsByIdAndMembers_Id(submission.getTeam().getId(), currentUser.getId());
        boolean isJudge = judgeRepository.existsByUser_IdAndHackathons_Id(currentUser.getId(), submission.getTeam().getHackathon().getId());
        boolean isOrganizer = submission.getTeam().getHackathon().getOrganizer().getId().equals(currentUser.getId());

        if (!isTeamMember && !isJudge && !isOrganizer) {
            throw new AccessDeniedException("Нет доступа к просмотру оценок");
        }

        List<SubmissionScoreHeader> headers = scoreHeaderRepository.findBySubmissionId(submissionId);
        List<SubmissionScoreResponseDTO.ScoreItem> items = new ArrayList<>();
        StringBuilder commentBuilder = new StringBuilder();

        for (SubmissionScoreHeader header : headers) {
            for (SubmissionScore score : header.getScores()) {
                SubmissionScoreResponseDTO.ScoreItem item = new SubmissionScoreResponseDTO.ScoreItem();
                item.setCriterionId(score.getCriterion().getId());
                item.setCriterionName(score.getCriterion().getName());
                item.setJudgeName(header.getJudge().getUser().getUsername());
                item.setScore(score.getScore());
                items.add(item);
            }
            commentBuilder.append("[").append(header.getJudge().getUser().getUsername()).append("]: ")
                    .append(header.getComment()).append("\n");
        }

        SubmissionScoreResponseDTO response = new SubmissionScoreResponseDTO();
        response.setSubmissionId(submissionId);
        response.setTeamName(submission.getTeam().getName());
        response.setScores(items);
        response.setScoreComment(commentBuilder.toString().trim());

        return response;
    }


}

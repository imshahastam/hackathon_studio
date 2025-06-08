package com.shaha.hackathon.controller;

import com.shaha.hackathon.team.Submission;
import com.shaha.hackathon.team.dto.*;
import com.shaha.hackathon.team.services.SubmissionScoreService;
import com.shaha.hackathon.team.services.SubmissionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;
    private final SubmissionScoreService submissionScoreService;

    //front done
    @PostMapping("/team/{teamId}")
    public ResponseEntity<SubmissionResponse> submitWork(
            @PathVariable Long teamId,
            @RequestBody SubmissionRequest request
    ) {
        Submission submission = submissionService.submitWork(teamId, request);
        return ResponseEntity.ok(toResponse(submission));
    }

    //front done
    @GetMapping("/team/{teamId}")
    public ResponseEntity<SubmissionResponse> getSubmission(@PathVariable Long teamId) {
        Submission submission = submissionService.getByTeam(teamId);
        return ResponseEntity.ok(toResponse(submission));
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<List<HackathonSubmissionDTO>> getHackathonSubmissions(@PathVariable Long hackathonId) {
        List<HackathonSubmissionDTO> submissions = submissionService.getHackathonSubmissions(hackathonId);
        return ResponseEntity.ok(submissions);
    }


    @PostMapping("/{submissionId}/scores")
    public ResponseEntity<?> submitScores(
            @PathVariable Long submissionId,
            @RequestBody SubmissionScoreRequestDTO request) {
        submissionScoreService.submitScores(submissionId, request.getScores(), request.getComment());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{submissionId}/scores")
    public ResponseEntity<SubmissionScoreResponseDTO> getSubmissionScores(@PathVariable Long submissionId) {
        return ResponseEntity.ok(submissionScoreService.getSubmissionScores(submissionId));
    }

    @Data
    public static class ScoreRequest {
        private Map<Long, Integer> scores; // criterionId -> score
        private String comment;
    }

    private SubmissionResponse toResponse(Submission submission) {
        return SubmissionResponse.builder()
                .id(submission.getId())
                .frontendRepo(submission.getFrontendRepo())
                .backendRepo(submission.getBackendRepo())
                .comment(submission.getComment())
                .submittedAt(submission.getSubmittedAt())
                .build();
    }
}

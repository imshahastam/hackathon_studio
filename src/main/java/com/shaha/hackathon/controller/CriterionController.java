package com.shaha.hackathon.controller;

import com.shaha.hackathon.hackathon.criterias.dtos.CriterionRequest;
import com.shaha.hackathon.hackathon.criterias.dtos.CriterionResponse;
import com.shaha.hackathon.hackathon.criterias.model.Criterion;
import com.shaha.hackathon.hackathon.criterias.services.CriterionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class CriterionController {
    private final CriterionService criterionService;

    @GetMapping("/hackathons/{hackathonId}/criteria")
    public ResponseEntity<List<CriterionResponse>> getCriteria(@PathVariable Long hackathonId) {
        return ResponseEntity.ok(criterionService.getCriteriaByHackathonId(hackathonId));
    }

    @PostMapping("/hackathons/{hackathonId}/add-criterias")
    public ResponseEntity<?> addCriterionsToHackathon(
            @PathVariable Long hackathonId,
            @RequestBody List<CriterionRequest> criteriaRequests
    ) {
        criterionService.addCriterionsToHackathon(hackathonId, criteriaRequests);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
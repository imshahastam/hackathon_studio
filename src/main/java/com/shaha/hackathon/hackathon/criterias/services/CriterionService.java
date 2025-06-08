package com.shaha.hackathon.hackathon.criterias.services;

import com.shaha.hackathon.hackathon.criterias.dtos.CriterionRequest;
import com.shaha.hackathon.hackathon.criterias.dtos.CriterionResponse;
import com.shaha.hackathon.hackathon.criterias.model.Criterion;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.repo.CriterionRepository;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CriterionService {
    private final CriterionRepository criterionRepository;
    private final HackathonRepository hackathonRepository;
    private final UserService userService;

    public List<CriterionResponse> getCriteriaByHackathonId(Long hackathonId) {
        List<Criterion> criterions = criterionRepository.findByHackathonId(hackathonId);
        return criterions.stream().map(CriterionResponse::new).toList();
    }

    public void addCriterionsToHackathon(Long hackathonId, List<CriterionRequest> requests) {
        Long currentUserId = userService.getCurrentUser().getId();
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));

        if (currentUserId.equals(hackathon.getOrganizer().getId())) {
            List<Criterion> criteria = requests.stream().map(req -> {
                Criterion criterion = new Criterion();
                criterion.setHackathon(hackathon);
                criterion.setName(req.getName());
                criterion.setDescription(req.getDescription());
                criterion.setMaxScore(req.getMaxScore());
                return criterion;
            }).toList();

            criterionRepository.saveAll(criteria);
        } else {
            throw new RuntimeException("Only organizer can add criterias!");
        }

    }

}

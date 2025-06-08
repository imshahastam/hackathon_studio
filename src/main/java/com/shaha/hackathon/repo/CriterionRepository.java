package com.shaha.hackathon.repo;

import com.shaha.hackathon.hackathon.criterias.model.Criterion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriterionRepository extends JpaRepository<Criterion, Long> {
    List<Criterion> findByHackathonId(Long hackathonId);
}

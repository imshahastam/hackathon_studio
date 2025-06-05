package com.shaha.hackathon.repo;

import com.shaha.hackathon.hackathon.model.HackathonApplication;
import com.shaha.hackathon.hackathon.model.HackathonApplicationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HackathonApplicationRepository extends JpaRepository<HackathonApplication, HackathonApplicationId> {
    List<HackathonApplication> findByHackathonIdAndStatus(Long hackathonId, HackathonApplication.ApplicationStatus status);
}
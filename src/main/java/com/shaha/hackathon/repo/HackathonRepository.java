package com.shaha.hackathon.repo;

import com.shaha.hackathon.hackathon.model.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonRepository extends JpaRepository<Hackathon, Integer> {
}

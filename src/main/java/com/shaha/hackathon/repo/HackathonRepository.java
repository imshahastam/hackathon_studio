package com.shaha.hackathon.repo;

import com.shaha.hackathon.hackathon.model.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HackathonRepository extends JpaRepository<Hackathon, Integer> {
    @Query("SELECT h FROM Hackathon h WHERE h.organizer.id = :organizerId")
    List<Hackathon> findByOrganizerId(@Param("organizerId") Long organizerId);
}

package com.shaha.hackathon.repo;

import com.shaha.hackathon.judge.models.Judge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JudgeRepository extends JpaRepository<Judge, Long> {
    @Query("SELECT j FROM Judge j JOIN j.hackathons h WHERE h.id = :hackathonId")
    List<Judge> findByHackathonId(@Param("hackathonId") Long hackathonId);

    Judge findByUserId(Long userId);

    boolean existsByUser_IdAndHackathons_Id(Long userId, Long hackathonId);
}

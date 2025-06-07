package com.shaha.hackathon.repo;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByHackathonId(Long hackathonId);

    boolean existsByHackathonAndMembersContains(Hackathon hackathon, User user);

    @Query("SELECT t FROM Team t LEFT JOIN t.members m WHERE t.leader.id = :userId OR m.id = :userId")
    List<Team> findAllByUserId(@Param("userId") Long userId);

    List<Team> findAllByHackathonId(Long hackathonId);
}


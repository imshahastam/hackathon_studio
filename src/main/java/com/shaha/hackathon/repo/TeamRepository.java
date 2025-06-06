package com.shaha.hackathon.repo;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByHackathonId(Long hackathonId);

    boolean existsByHackathonAndMembersContains(Hackathon hackathon, User user);
}


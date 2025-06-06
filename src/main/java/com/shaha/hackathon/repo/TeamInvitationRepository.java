package com.shaha.hackathon.repo;

import com.shaha.hackathon.judge.models.InvitationStatus;
import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.team.TeamInvitation;
import com.shaha.hackathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, Long> {
    Optional<TeamInvitation> findByTeamAndInvited(Team team, User user);
    List<TeamInvitation> findByInvitedIdAndStatus(Long userId, InvitationStatus status);
}


package com.shaha.hackathon.team;

import com.shaha.hackathon.judge.models.InvitationStatus;
import com.shaha.hackathon.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_invitations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Team team;

    @ManyToOne
    User invited;

    @Enumerated(EnumType.STRING)
    InvitationStatus status = InvitationStatus.PENDING;

    LocalDateTime sentAt = LocalDateTime.now();
}


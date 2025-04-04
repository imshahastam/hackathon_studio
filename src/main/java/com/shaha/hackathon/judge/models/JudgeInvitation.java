package com.shaha.hackathon.judge.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Table(name="judge_invitations")
public class JudgeInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long hackathonId;
    Long judgeId;

    @Enumerated(EnumType.STRING)
    InvitationStatus status;

    LocalDateTime createdAt;

    public JudgeInvitation() {}

    public JudgeInvitation(Long hackathonId, Long judgeId) {
        this.hackathonId = hackathonId;
        this.judgeId = judgeId;
        this.status = InvitationStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
}

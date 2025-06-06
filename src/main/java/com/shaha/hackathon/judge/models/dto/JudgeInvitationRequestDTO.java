package com.shaha.hackathon.judge.models.dto;

import com.shaha.hackathon.hackathon.model.dto.HackathonForJudgeInvitationDTO;
import com.shaha.hackathon.judge.models.InvitationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JudgeInvitationRequestDTO {
    Long id;
    Long hackathonId;
    HackathonForJudgeInvitationDTO hackathon;
    Long judgeId;
    InvitationStatus status;
    LocalDateTime createdAt;
}

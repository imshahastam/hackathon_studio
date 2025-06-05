package com.shaha.hackathon.judge.models.dto;

import com.shaha.hackathon.judge.models.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeInvitationDTO {
    private Long judgeId;
    private InvitationStatus status;
}

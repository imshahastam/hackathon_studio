package com.shaha.hackathon.judge.models.dto;

import com.shaha.hackathon.judge.models.InvitationStatus;

public class RespondToInvitationRequest {
    private InvitationStatus action; // ACCEPT or DECLINE

    public InvitationStatus getAction() {
        return action;
    }

    public void setAction(InvitationStatus action) {
        this.action = action;
    }
}

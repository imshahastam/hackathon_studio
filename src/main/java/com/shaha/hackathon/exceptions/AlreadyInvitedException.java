package com.shaha.hackathon.exceptions;

public class AlreadyInvitedException extends RuntimeException {
    public AlreadyInvitedException(String message) {
        super(message);
    }
}

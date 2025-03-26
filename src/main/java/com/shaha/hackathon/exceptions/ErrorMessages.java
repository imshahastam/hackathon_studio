package com.shaha.hackathon.exceptions;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    HACKATHON_NOT_FOUND("Hackathon with ID %s not found"),
    NAME_REQUIRED("Hackathon name is required"),
    DESCRIPTION_LENGTH("Description must be at least %d characters"),
    INVALID_DATE_RANGE("Start date %s cannot be after end date %s");

    private final String messageTemplate;

    ErrorMessages(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String format(Object... args) {
        return String.format(messageTemplate, args);
    }
}

package com.shaha.hackathon.exceptions;

import lombok.Getter;

@Getter
public class HackathonExceptions extends RuntimeException{
    private final String errorCode;

    public HackathonExceptions(ErrorMessages errorMessage, Object... args) {
        super(errorMessage.format(args));
        this.errorCode = errorMessage.name();
    }
}

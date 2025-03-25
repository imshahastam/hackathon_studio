package com.shaha.hackathon.hackathon.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateHackathonCommand {
    Long id;
    Hackathon hackathon;

    public UpdateHackathonCommand(Long id, Hackathon hackathon) {
        this.id = id;
        this.hackathon = hackathon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hackathon getHackathon() {
        return hackathon;
    }

    public void setHackathon(Hackathon hackathon) {
        this.hackathon = hackathon;
    }
}

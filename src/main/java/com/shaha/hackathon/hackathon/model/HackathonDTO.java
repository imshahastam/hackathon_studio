package com.shaha.hackathon.hackathon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shaha.hackathon.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HackathonDTO {
    private String name;
    private String description;
    private HackathonStatus status;
    private HackathonType type;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    private String location;
    private Long organizerId;

    public HackathonDTO(Hackathon hackathon) {
        this.name = hackathon.getName();
        this.description = hackathon.getDescription();
        this.status = hackathon.getStatus();
        this.type = hackathon.getType();
        this.startDate = hackathon.getStartDate();
        this.endDate = hackathon.getEndDate();
        this.location = hackathon.getLocation();
        this.organizerId = hackathon.getOrganizer().getId();
    }
}

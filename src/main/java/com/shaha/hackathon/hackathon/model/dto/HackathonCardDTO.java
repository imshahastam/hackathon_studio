package com.shaha.hackathon.hackathon.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonStatus;
import com.shaha.hackathon.hackathon.model.HackathonType;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.user.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class HackathonCardDTO {
    private Long id;
    private String name;
    private HackathonStatus status;
    private HackathonType type;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    private String location;
    private Long organizerId;
    private Set<String> tags;
    private BigDecimal prizeFund;

    public HackathonCardDTO(Hackathon hackathon) {
        this.id = hackathon.getId();
        this.name = hackathon.getName();
        this.status = hackathon.getStatus();
        this.type = hackathon.getType();
        this.startDate = hackathon.getStartDate();
        this.endDate = hackathon.getEndDate();
        this.location = hackathon.getLocation();
        this.organizerId = hackathon.getOrganizer().getId();
        this.tags = hackathon.getTags().stream()
                .map(Competence::getName)
                .collect(Collectors.toSet());
        this.prizeFund = hackathon.getPrizeFund();
    }
}

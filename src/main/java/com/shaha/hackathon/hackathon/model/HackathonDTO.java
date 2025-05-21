package com.shaha.hackathon.hackathon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.judge.models.dto.TagDTO;
import com.shaha.hackathon.user.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<Long> participantIds;
    private List<TagDTO> tags;
    private BigDecimal prizeFund;
    private String conditions;

    public HackathonDTO(Hackathon hackathon) {
        this.name = hackathon.getName();
        this.description = hackathon.getDescription();
        this.status = hackathon.getStatus();
        this.type = hackathon.getType();
        this.startDate = hackathon.getStartDate();
        this.endDate = hackathon.getEndDate();
        this.location = hackathon.getLocation();
        this.organizerId = hackathon.getOrganizer().getId();
        this.participantIds = hackathon.getParticipants().stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        this.tags = hackathon.getTags().stream()
                .map(TagDTO::new)
                .toList();
        this.prizeFund = hackathon.getPrizeFund();
        this.conditions = hackathon.getConditions();
    }
}

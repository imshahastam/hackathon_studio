package com.shaha.hackathon.hackathon.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonStatus;
import com.shaha.hackathon.hackathon.model.HackathonType;
import com.shaha.hackathon.judge.models.Competence;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class HackathonCreateDTO {
    String name;
    String description;
    HackathonStatus status;
    HackathonType type;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("start_date")
    LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("end_date")
    LocalDateTime endDate;

    String location;
    List<Long> tagsId;
    BigDecimal prizeFund;
    String conditions;

    public HackathonCreateDTO(Hackathon hackathon) {
        this.name = hackathon.getName();
        this.status = hackathon.getStatus();
        this.type = hackathon.getType();
        this.startDate = hackathon.getStartDate();
        this.endDate = hackathon.getEndDate();
        this.location = hackathon.getLocation();
        this.tagsId = hackathon.getTags().stream()
                .map(Competence::getId)
                .collect(Collectors.toList());
        this.prizeFund = hackathon.getPrizeFund();
    }
}

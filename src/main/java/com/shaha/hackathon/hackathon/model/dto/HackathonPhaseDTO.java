package com.shaha.hackathon.hackathon.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaha.hackathon.hackathon.model.PhaseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonPhaseDTO {
    Long id;
    String name;
    String description;
    PhaseType type;
    LocalDateTime startTime;
    LocalDateTime endTime;
    @JsonProperty("isSystem")
    boolean isSystem;
}

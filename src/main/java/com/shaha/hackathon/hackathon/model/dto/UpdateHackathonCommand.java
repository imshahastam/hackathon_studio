package com.shaha.hackathon.hackathon.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonStatus;
import com.shaha.hackathon.hackathon.model.HackathonType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHackathonCommand {
    Long id;
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
    List<String> newTags;
    BigDecimal prizeFund;
    String conditions;
}

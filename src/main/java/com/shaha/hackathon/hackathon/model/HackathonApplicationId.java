package com.shaha.hackathon.hackathon.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class HackathonApplicationId implements Serializable {
    private Long userId;
    private Long hackathonId;

    // equals, hashCode
}

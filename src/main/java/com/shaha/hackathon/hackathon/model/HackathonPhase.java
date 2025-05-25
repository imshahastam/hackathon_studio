package com.shaha.hackathon.hackathon.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "hackathon_phases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonPhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

    String name;
    String description;

    @Enumerated(EnumType.STRING)
    PhaseType type;

    LocalDateTime startTime;
    LocalDateTime endTime;

    boolean isSystem;
}

package com.shaha.hackathon.team;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String frontendRepo;
    private String backendRepo;

    @Column(length = 2000)
    private String comment;

    private LocalDateTime submittedAt;

    @OneToOne
    @JoinColumn(name = "team_id", unique = true)
    private Team team;
}


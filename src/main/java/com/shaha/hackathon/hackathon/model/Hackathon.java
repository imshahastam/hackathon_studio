package com.shaha.hackathon.hackathon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.judge.models.Judge;
import com.shaha.hackathon.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="hackathons")
public class Hackathon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hackathon name is required")
    @Size(min = 3, max = 100, message = "Hackathon name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 20, message = "Description must be at least 20 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HackathonStatus status; // ENUM

    @Enumerated(EnumType.STRING)
    private HackathonType type; // ENUM

    @NotNull(message = "Start date is required")
    //@FutureOrPresent(message = "Start date cannot be in the past")
    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    //@Future(message = "End date must be in the future")
    @JsonProperty("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "end_date")
    private LocalDateTime endDate;
    private String location;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonProperty("organizer_id")
    private User organizer;

    @ManyToMany(mappedBy = "hackathons")
    private Set<User> participants = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "hackathon_competences",
            joinColumns = @JoinColumn(name = "hackathon_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private Set<Competence> tags = new HashSet<>();

    @DecimalMin(value = "0.0", inclusive = false, message = "Prize fund must be greater than zero")
    @Column(name = "prize_fund")
    private BigDecimal prizeFund;

    @NotBlank(message = "Conditions are required")
    @Column(columnDefinition = "TEXT")
    private String conditions;

    @ManyToMany
    @JoinTable(
            name = "hackathon_judges",
            joinColumns = @JoinColumn(name = "hackathon_id"),
            inverseJoinColumns = @JoinColumn(name = "judge_id")
    )
    private Set<Judge> judges = new HashSet<>();

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

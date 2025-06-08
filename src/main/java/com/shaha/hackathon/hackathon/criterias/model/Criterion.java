package com.shaha.hackathon.hackathon.criterias.model;

import com.shaha.hackathon.hackathon.model.Hackathon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "criteria")
@Getter
@Setter
public class Criterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    private String name;
    private Integer maxScore;
    private String description;

}

package com.shaha.hackathon.team;

import com.shaha.hackathon.hackathon.criterias.model.Criterion;
import com.shaha.hackathon.judge.models.Judge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "submission_scores")
@Getter
@Setter
public class SubmissionScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SubmissionScoreHeader scoreHeader;

    @ManyToOne
    @JoinColumn(name = "criterion_id")
    private Criterion criterion;

    private Integer score;

}

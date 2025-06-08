package com.shaha.hackathon.team;

import com.shaha.hackathon.judge.models.Judge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "submission_score_headers")
@Getter
@Setter
public class SubmissionScoreHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    @ManyToOne
    @JoinColumn(name = "judge_id")
    private Judge judge;

    private String comment;  // общий комментарий

    @OneToMany(mappedBy = "scoreHeader", cascade = CascadeType.ALL)
    private List<SubmissionScore> scores;
}

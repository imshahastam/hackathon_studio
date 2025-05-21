package com.shaha.hackathon.judge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@Table(name="judges")
public class Judge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonProperty("user_id")
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
    String company;

    @JsonProperty("work_experience")
    @Column(name = "work_experience")
    Integer workExperience;

    @Column(columnDefinition = "TEXT")
    String bio;
    String linkedin;

    @JsonIgnore
    @ManyToMany(mappedBy = "judges")
    Set<Hackathon> hackathons = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "judge_competences",
            joinColumns = @JoinColumn(name = "judge_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    Set<Competence> competences = new HashSet<>();

}

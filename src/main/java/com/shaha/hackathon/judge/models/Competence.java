package com.shaha.hackathon.judge.models;

import com.shaha.hackathon.hackathon.model.Hackathon;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Table(name="competences")
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Name is required")
    @Column(unique = true, nullable = false)
    String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Hackathon> hackathons = new HashSet<>();

}

package com.shaha.hackathon.judge.services;

import com.shaha.hackathon.Query;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.repo.CompetenceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllTagsService implements Query<Void, List<Competence>> {
    private final CompetenceRepository competenceRepository;

    public GetAllTagsService(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    @Override
    public ResponseEntity<List<Competence>> execute(Void input) {
        List<Competence> tags = competenceRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }
}

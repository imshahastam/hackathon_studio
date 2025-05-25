package com.shaha.hackathon.judge.services;

import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.repo.CompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SaveNewAndExistingTagsService {
    private final CompetenceRepository competenceRepository;

    public SaveNewAndExistingTagsService(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    public Set<Competence> execute(Collection<Long> ids, Collection<String> names) {
        Set<Competence> result = new HashSet<>();

        // 1. Существующие теги по id
        if (ids != null && !ids.isEmpty()) {
            result.addAll(competenceRepository.findAllById(ids));
        }

        // 2. Новые теги по name
        if (names != null && !names.isEmpty()) {
            for (String name : names) {
                Competence tag = competenceRepository.findByName(name)
                        .orElseGet(() -> competenceRepository.save(new Competence(name)));
                result.add(tag);
            }
        }

        return result;
    }
}

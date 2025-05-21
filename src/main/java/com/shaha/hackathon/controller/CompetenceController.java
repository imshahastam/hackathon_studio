package com.shaha.hackathon.controller;

import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.judge.services.GetAllTagsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tags")
public class CompetenceController {
    private final GetAllTagsService getAllTagsService;

    CompetenceController(GetAllTagsService getAllTagsService) {
        this.getAllTagsService = getAllTagsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Competence>> getTags() {
        return getAllTagsService.execute(null);
    }
}

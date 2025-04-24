package org.biostudies_demo.api.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.biostudies_demo.api.study.model.Study;
import org.biostudies_demo.api.study.model.StudyRepository;
import org.biostudies_demo.api.study.service.IndexingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/indexer")
public class IndexerController {
    private final IndexingService indexingService;
    private final StudyRepository studyRepository;

    public IndexerController(IndexingService indexingService, StudyRepository studyRepository) {
        this.indexingService = indexingService;
        this.studyRepository = studyRepository;
    }

    @PostMapping
    public ResponseEntity<String> indexStudies() throws IOException {
        log.info("Init index process");
        List<Study> studies = studyRepository.findAll();
        indexingService.indexStudySummaries(studies);
        log.info("End index process");
        return ResponseEntity.ok("Data Indexed");
    }
}

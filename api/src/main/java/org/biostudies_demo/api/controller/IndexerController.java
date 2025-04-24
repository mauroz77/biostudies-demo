package org.biostudies_demo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.biostudies_demo.api.model.Study;
import org.biostudies_demo.api.model.StudyRepository;
import org.biostudies_demo.api.service.IndexingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Endpoints to index studies with Lucene.
 */
@Tag(name = "Studies Indexing Process", description = "Indexes Studies")
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

    /**
     * Indexes all studies in the system.
     *
     * @return ResponseEntity containing the indexing result as a String with HTTP status
     */
    @Operation(
            summary = "Index studies",
            description = "Triggers the indexing process for all studies in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Studies indexed successfully",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred during the indexing process"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<String> indexStudies() throws IOException {
        log.info("Init index process");
        List<Study> studies = studyRepository.findAll();
        indexingService.indexStudySummaries(studies);
        log.info("End index process");
        return ResponseEntity.ok("Data Indexed");
    }
}

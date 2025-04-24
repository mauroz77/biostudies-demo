package org.biostudies_demo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.biostudies_demo.api.service.SearchResponse;
import org.biostudies_demo.api.dto.StudyDTO;
import org.biostudies_demo.api.mappers.StudyMapper;
import org.biostudies_demo.api.service.StudyService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Endpoints to search and retrieve studies.
 * This controller handles all study-related API operations including search and retrieval.
 */
@Tag(name = "Search", description = "API endpoints for searching and managing studies")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final StudyMapper studyMapper;

    /**
     * Searches for studies based on the provided query parameters.
     *
     * @param q The search query text
     * @param page The page number for pagination, starting at 1
     * @param pageSize Number of results per page
     * @param sortBy Field to sort the results by
     * @param sortOrder Direction of sorting ('ascending' or 'descending')
     * @return SearchResponse containing the search results and metadata
     */
    @Operation(
            summary = "Search studies",
            description = "Searches for studies based on the provided query and pagination parameters",
            parameters = {
                    @Parameter(name = "q", description = "Search query text", required = true),
                    @Parameter(name = "page", description = "Page number (starting at 1)", schema = @Schema(type = "integer", defaultValue = "1")),
                    @Parameter(name = "pageSize", description = "Number of results per page", schema = @Schema(type = "integer", defaultValue = "10")),
                    @Parameter(name = "sortBy", description = "Field to sort the results by", schema = @Schema(type = "string", defaultValue = "")),
                    @Parameter(name = "sortOrder", description = "Direction of sorting", schema = @Schema(type = "string", defaultValue = "descending",
                            allowableValues = {"ascending", "descending"}))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Search completed successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid search parameters provided"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred during search operation"
                    )
            }
    )
    @GetMapping("/search")
    public SearchResponse search(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") String sortBy,
            @RequestParam(defaultValue = "descending") String sortOrder
    ) throws IOException, ParseException {
        return studyService.search(q, page, pageSize, sortBy, sortOrder);
    }

    /**
     * Retrieves a specific study by its accession number.
     *
     * @param accno The unique accession number identifier of the study
     * @return StudyDTO containing the complete study information
     */
    @Operation(
            summary = "Get study details",
            description = "Retrieves detailed information for a specific study using its accession number",
            parameters = {
                    @Parameter(
                            name = "accno",
                            description = "The unique accession number of the study",
                            required = true,
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Study found and returned successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudyDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Study with specified accession number not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred while retrieving the study"
                    )
            }
    )
    @GetMapping("/study/{accno}")
    public StudyDTO getStudy(@PathVariable String accno) {
        return studyMapper.studyToStudyDto(studyService.getStudy(accno));
    }
}

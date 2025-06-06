package org.biostudies_demo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.biostudies_demo.api.model.Study;
import org.biostudies_demo.api.model.StudyRepository;

import org.biostudies_demo.api.service.BioStudiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller in charge of talking to the BioStudies API.
 */
@Tag(name = "Data Loading", description = "Load data from BioStudies")
@Slf4j
@RestController
@RequestMapping("/api/biostudies")
public class BioStudiesController {

    private final BioStudiesService bioStudiesService;
    private final StudyRepository studyRepository;

    // List of accession IDs of studies to download
    private static final String[] IDS = {
            "S-EPMC7778976", "S-EPMC5753258", "S-EPMC7614751", "S-EPMC11426508", "S-EPMC10587067",
            "S-EPMC8468516", "S-EPMC6386259", "S-EPMC4078858", "S-EPMC2570937", "S-EPMC6501942",
            "S-EPMC7343405", "S-EPMC3310452", "S-EPMC5014377", "S-EPMC10291558", "S-EPMC5019256",
            "S-EPMC8185012", "S-EPMC3471617", "S-EPMC5029496", "S-EPMC7164598", "S-EPMC7123135",
            "S-ECPF-GEOD-28901", "S-EPMC2530869", "E-GEOD-28901", "S-EPMC1888820", "E-MEXP-1172",
            "S-EPMC2764720", "S-EPMC1796608", "S-EPMC5054164", "S-ECPF-GEOD-39704", "S-EPMC16833",
            "S-EPMC3558343", "S-ECPF-GEOD-13947", "E-GEOD-6922", "S-ECPF-GEOD-60017", "S-EPMC2848623",
            "S-EPMC11622835", "S-EPMC2716688", "S-EPMC10425453", "S-EPMC7254253", "S-EPMC10859268",
            "S-EPMC7014259", "S-EPMC9485382", "S-EPMC7850474", "S-EPMC10396002", "S-EPMC3431233",
            "S-EPMC6696787", "S-EPMC6393212", "S-EPMC5554542", "S-EPMC9374219", "S-EPMC4892737"
    };

    public BioStudiesController(BioStudiesService bioStudiesService, StudyRepository studyRepository) {
        this.bioStudiesService = bioStudiesService;
        this.studyRepository = studyRepository;
    }

    /**
     * Fetches some studies from BioStudies and store them into the DB.
     *
     * @return ResponseEntity containing a String response with HTTP status
     */
    @Operation(
            summary = "Load studies sample",
            description = "Loads a sample of studies data",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully loaded studies sample",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred while loading studies sample"
                    )
            }
    )
    @PostMapping("/studies/")
    public ResponseEntity<String> loadStudiesSample() {
        int total = IDS.length;
        int found = 0;
        for (int i = 0; i < total; i++) {
            log.info("Downloading {} ({} from {})", IDS[i], i + 1, total);
            Study study = bioStudiesService.getStudyByAccession(IDS[i]);
            if (study != null && study.getAccno() != null) {
                log.info("Study: {}", study.getAccno());
                found++;
                studyRepository.save(study);
            } else {
                log.error("Study not found: {}", IDS[i]);
            }
        }
        return ResponseEntity.ok("Found " + found);
    }

    /**
     * Retrieves a study by its accession number.
     *
     * @param accession The unique accession identifier of the study to retrieve
     * @return ResponseEntity containing the study information as a String with HTTP status
     */
    @Operation(
            summary = "Fetches a study by accession and stores it into the DB",
            description = "Retrieves a specific study using its unique accession identifier",
            parameters = {
                    @Parameter(
                            name = "accession",
                            description = "The unique accession identifier of the study",
                            required = true,
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Study found and saved successfully",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Study with specified accession not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred while retrieving or saving the study"
                    )
            }
    )
    @GetMapping("/studies/{accession}")
    public ResponseEntity<String> getStudy(@PathVariable String accession) {
        Study study = bioStudiesService.getStudyByAccession(accession);
        log.info("Study fetched correctly");
        studyRepository.save(study);
        log.info("Study saved. ID: {}", study.getId());
        if (study != null) {
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.notFound().build();
    }

}

package org.biostudies_demo.api.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.biostudies_demo.api.study.model.Study;
import org.biostudies_demo.api.study.model.StudyRepository;
import org.biostudies_demo.api.study.repository.BioStudiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/biostudies")
public class BioStudiesController {

    private final BioStudiesService bioStudiesService;
    private final StudyRepository studyRepository;

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

    @PostMapping("/studies/")
    public ResponseEntity<String> loadStudiesSample() {
        int total = IDS.length;
        int found = 0;
        for (int i = 0; i < total; i++) {
            log.info("Downloading {} ({} from {})", IDS[i], i+1, total);
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

    @GetMapping("/studies/{accession}")
    public ResponseEntity<String> getStudy(@PathVariable String accession) throws MalformedURLException {
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

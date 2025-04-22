package org.biostudies_demo.api.study.controller;

import lombok.RequiredArgsConstructor;
import org.biostudies_demo.api.study.dto.SearchResponseDTO;
import org.biostudies_demo.api.study.dto.StudyDTO;
import org.biostudies_demo.api.study.mappers.StudyMapper;
import org.biostudies_demo.api.study.service.StudyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final StudyMapper studyMapper;

    @GetMapping("/search")
    public SearchResponseDTO search(@RequestParam String q) {
        //return studyService.search(q);
        return new SearchResponseDTO();
    }

    @GetMapping("/study/{accno}")
    public StudyDTO getStudy(@PathVariable String accno) {
        return studyMapper.studyToStudyDto(studyService.getStudy(accno));
    }
}

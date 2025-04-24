package org.biostudies_demo.api.study.controller;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.biostudies_demo.api.study.service.SearchResponse;
import org.biostudies_demo.api.study.dto.StudyDTO;
import org.biostudies_demo.api.study.mappers.StudyMapper;
import org.biostudies_demo.api.study.service.StudyService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final StudyMapper studyMapper;

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

    @GetMapping("/study/{accno}")
    public StudyDTO getStudy(@PathVariable String accno) {
        return studyMapper.studyToStudyDto(studyService.getStudy(accno));
    }
}

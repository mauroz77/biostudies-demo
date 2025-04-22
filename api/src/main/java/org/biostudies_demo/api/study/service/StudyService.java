package org.biostudies_demo.api.study.service;

import org.biostudies_demo.api.study.dto.StudyHitDTO;
import org.biostudies_demo.api.study.model.Study;
import org.biostudies_demo.api.study.model.StudyRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public List<StudyHitDTO> search(String q) {
        return new ArrayList<>();
    }

    public Study getStudy(String accno) {
        return studyRepository.findByAccno(accno);
    }
}

package org.biostudies_demo.api.service;

import org.apache.lucene.queryparser.classic.ParseException;
import org.biostudies_demo.api.model.Study;
import org.biostudies_demo.api.model.StudyRepository;
import org.biostudies_demo.api.model.StudySection;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StudyService {

    private final StudyRepository studyRepository;
    private final SearchingService searchingService;

    public StudyService(StudyRepository studyRepository, SearchingService searchingService) {
        this.studyRepository = studyRepository;
        this.searchingService = searchingService;
    }

    public SearchResponse search(String q, int page, int pageSize, String sortBy, String sortOrder) throws IOException, ParseException {
        // First get results from the index
        LuceneSearchResult luceneResult = searchingService.search(q, page, pageSize);
        List<Study> studies = studyRepository.findByAccnoIn(luceneResult.getAccnos());

        List<SearchHit> hits = studies.stream()
                .map(this::createSearchHit)
                .toList();


        SearchResponse response = new SearchResponse();
        response.setPage(page);
        response.setPageSize(pageSize);
        response.setTotalHits(luceneResult.getTotalHits());
        response.setTotalHitsExact(luceneResult.isTotalHitsExact());
        response.setSortBy(this.sortBy);
        response.setSortOrder(sortOrder);
        response.setHits(hits);
        response.setSortBy(sortBy);
        response.setSortOrder(sortOrder);
        return response;

/*
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.set
        List<SearchHit> searchHits = new ArrayList<>();
        List<StudySummary> studySummaries = searchingService.search(q);
        for (StudySummary studySummary : studySummaries) {
            Study study = studyRepository.findByAccno(studySummary.getAccno());
            if (study != null) {
                SearchHit searchHit = createSearchHit(study);
                searchHits.add(searchHit);
            }
        }

        // With the summaries we can obtain the studies to create the StudyHitDTO

        return new ArrayList<>();*/
    }

    private int page;
    private int pageSize;
    private long totalHits;
    private boolean isTotalHitsExact;
    private String sortBy;
    private String sortOrder;
    private List<SearchHit> hits;

    private SearchHit createSearchHit(Study study) {
        SearchHit searchHit = new SearchHit();
        searchHit.setAccession(study.getAccno());
        searchHit.setType(study.getType());
        searchHit.setTitle(study.getTitle());
        searchHit.setAuthor(study.getAuthorsAsText());
        searchHit.setLinks(countNumberLinks(study));
        searchHit.setFiles(countNumberFiles(study));
        searchHit.setReleaseDate(LocalDate.parse(study.getReleaseDate()));
        return searchHit;
    }

    private int countNumberLinks(Study study) {
        AtomicInteger number = new AtomicInteger();
        // Assumes only subsections have links
        List<StudySection> studySections = study.getSection().getSubsections();
        if (studySections != null) {
            for (StudySection studySection : studySections) {
                studySection.getLinks().forEach(g -> {
                    number.addAndGet(g.getLinks().size());
                });
            }
        }
        return number.get();
    }

    private int countNumberFiles(Study study) {
        AtomicInteger number = new AtomicInteger();
        // Assumes only subsections have files
        List<StudySection> studySections = study.getSection().getSubsections();
        if (studySections != null) {
            for (StudySection studySection : studySections) {
                number.addAndGet(studySection.getFiles().size());
            }
        }
        return number.get();
    }

    public Study getStudy(String accno) {
        return studyRepository.findByAccno(accno);
    }
}

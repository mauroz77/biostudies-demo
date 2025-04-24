package org.biostudies_demo.api.service;

import lombok.Data;

import java.util.List;

@Data
public class LuceneSearchResult {
    private List<String> accnos;
    private long totalHits;
    private boolean isTotalHitsExact;
}

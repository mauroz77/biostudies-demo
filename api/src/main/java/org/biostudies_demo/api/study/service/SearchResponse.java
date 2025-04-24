package org.biostudies_demo.api.study.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private int page;
    private int pageSize;
    private long totalHits;
    private boolean isTotalHitsExact;
    private String sortBy;
    private String sortOrder;
    private List<SearchHit> hits;
}

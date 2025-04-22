package org.biostudies_demo.api.study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchHitDTO {
    private String accession;
    private String type;
    private String title;
    private String author;
    private int links;
    private int files;
    @JsonProperty("release_date")
    private LocalDate releaseDate;  // Could also use LocalDate with proper serialization
    private int views;
    //@JsonProperty("isPublic")
    private boolean isPublic;
}

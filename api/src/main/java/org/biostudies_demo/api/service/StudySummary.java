package org.biostudies_demo.api.service;

import lombok.Data;

@Data
public class StudySummary {
    private String accno;
    private String title;
    private String abstractText;
    private String authors;
}

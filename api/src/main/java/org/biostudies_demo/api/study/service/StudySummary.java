package org.biostudies_demo.api.study.service;

import lombok.Data;

@Data
public class StudySummary {
    private String accno;
    private String title;
    private String abstractText;
    private String authors;
}

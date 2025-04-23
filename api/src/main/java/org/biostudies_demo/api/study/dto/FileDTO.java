package org.biostudies_demo.api.study.dto;

import lombok.Data;

@Data
public class FileDTO {
    private String path;
    private long size;
    private String type;
}

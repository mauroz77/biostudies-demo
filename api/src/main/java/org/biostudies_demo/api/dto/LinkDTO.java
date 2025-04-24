package org.biostudies_demo.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class LinkDTO {
    private String url;
    private List<AttributeDTO> attributes;
}

package org.biostudies_demo.api.study.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionDTO {
    @JsonProperty("accno")
    private String accessionNumber;
    private String type;
    private List<AttributeDTO> attributes;
    private List<FileDTO> files;
    private List<List<LinkDTO>> links;
    private List<SectionDTO> subsections;
}

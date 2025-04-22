package org.biostudies_demo.api.study.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StudyDTO {

    @JsonProperty("accno")
    private String accessionNumber;

    private String type;

    private List<AttributeDTO> attributes;
}

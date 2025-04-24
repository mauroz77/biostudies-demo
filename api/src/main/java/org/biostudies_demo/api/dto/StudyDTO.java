package org.biostudies_demo.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyDTO {

    @JsonProperty("accno")
    private String accessionID;

    private String type;

    private List<AttributeDTO> attributes;

    private SectionDTO section;
}

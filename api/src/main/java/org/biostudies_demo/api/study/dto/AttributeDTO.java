package org.biostudies_demo.api.study.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AttributeDTO {
    private String name;
    private String value;
    private Boolean reference;
}

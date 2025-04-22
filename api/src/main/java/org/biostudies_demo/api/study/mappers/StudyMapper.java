package org.biostudies_demo.api.study.mappers;

import org.biostudies_demo.api.study.dto.AttributeDTO;
import org.biostudies_demo.api.study.dto.StudyDTO;
import org.biostudies_demo.api.study.model.Attribute;
import org.biostudies_demo.api.study.model.Study;
import org.biostudies_demo.api.study.model.StudyAttribute;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudyMapper {
    public StudyDTO studyToStudyDto(Study study) {
        StudyDTO studyDTO = new StudyDTO();
        if (study != null) {
            studyDTO.setAccessionNumber(study.getAccno());
            studyDTO.setType(study.getType());
            List<AttributeDTO> studyAttributes = studyAttributesToAttributesDTOs(study.getAttributes());
            studyDTO.setAttributes(studyAttributes);
        }
        return studyDTO;
    }

    private List<AttributeDTO> studyAttributesToAttributesDTOs(List<StudyAttribute> studyAttributes) {
        List<AttributeDTO> attributesDTOs = new ArrayList<>();
        if (studyAttributes != null) {
            studyAttributes.forEach(sa -> {
                AttributeDTO attributeDTO = new AttributeDTO();
                Attribute attribute = sa.getAttribute();
                if (attribute != null) {
                    attributeDTO.setName(attribute.getName());
                    attributeDTO.setValue(attribute.getValue());
                }
                attributesDTOs.add(attributeDTO);

            });
        }
        return attributesDTOs;
    }
}

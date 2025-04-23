package org.biostudies_demo.api.study.mappers;

import org.biostudies_demo.api.study.dto.*;
import org.biostudies_demo.api.study.model.*;
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
            studyDTO.setSection(studySectionToSectionDTO(study.getSection()));
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

    private SectionDTO studySectionToSectionDTO(StudySection studySection) {
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setAccessionNumber(studySection.getAccno());
        sectionDTO.setType(studySection.getType());
        sectionDTO.setAttributes(studySectionAttributesToAttributesDTOs(studySection.getAttributes()));
        sectionDTO.setFiles(filesToFilesDTOs(studySection.getFiles()));
        sectionDTO.setLinks(studyLinkGroupToLinkGroupDTOs(studySection.getLinks()));
        sectionDTO.setSubsections(studySubsectionsToSectionsDTOs(studySection.getSubsections()));
        return sectionDTO;
    }

    private List<AttributeDTO> studySectionAttributesToAttributesDTOs(List<StudySectionAttribute> studySectionAttributes) {
        List<AttributeDTO> attributesDTOs = new ArrayList<>();
        if (studySectionAttributes != null) {
            studySectionAttributes.forEach(sa -> {
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

    private List<FileDTO> filesToFilesDTOs(List<StudyFile> studyFiles) {
        List<FileDTO> fileDTOS = new ArrayList<>();
        if (studyFiles != null) {
            studyFiles.forEach(studyFile -> {
                FileDTO fileDTO = new FileDTO();
                fileDTO.setPath(studyFile.getPath());
                fileDTO.setSize(studyFile.getSize());
                fileDTO.setType(studyFile.getType());
                fileDTOS.add(fileDTO);
            });
        }
        return fileDTOS;
    }

    private List<List<LinkDTO>> studyLinkGroupToLinkGroupDTOs(List<StudyLinkGroup> studyLinkGroups) {
        List<List<LinkDTO>> groupsLinks = new ArrayList<>();
        if (studyLinkGroups != null) {
            studyLinkGroups.forEach(studyLinkGroup -> {
                List<LinkDTO> links = studyLinkGroupToLinksDTOs(studyLinkGroup);
                groupsLinks.add(links);
            });
        }
        return groupsLinks;
    }

    private List<LinkDTO> studyLinkGroupToLinksDTOs(StudyLinkGroup studyLinkGroup) {
        List<LinkDTO> linkDTOS = new ArrayList<>();
        if (studyLinkGroup != null && studyLinkGroup.getLinks() != null) {
            studyLinkGroup.getLinks().forEach(studyLink -> {
                LinkDTO linkDTO = new LinkDTO();
                linkDTO.setUrl(studyLink.getUrl());
                linkDTO.setAttributes(studyLinkAttributesToAttributesDTOs(studyLink.getAttributes()));
                linkDTOS.add(linkDTO);

            });
        }
        return linkDTOS;
    }

    private List<AttributeDTO> studyLinkAttributesToAttributesDTOs(List<StudyLinkAttribute> studyLinkAttributes) {
        List<AttributeDTO> attributesDTOs = new ArrayList<>();
        if (studyLinkAttributes != null) {
            studyLinkAttributes.forEach(sla -> {
                AttributeDTO attributeDTO = new AttributeDTO();
                Attribute attribute = sla.getAttribute();
                if (attribute != null) {
                    attributeDTO.setName(attribute.getName());
                    attributeDTO.setValue(attribute.getValue());
                }
                attributesDTOs.add(attributeDTO);
            });
        }
        return attributesDTOs;
    }

    private List<SectionDTO> studySubsectionsToSectionsDTOs(List<StudySection> subsections) {
        List<SectionDTO> sectionDTOs = new ArrayList<>();
        if (subsections != null) {
            subsections.forEach(studySection -> {
                SectionDTO sectionDTO = studySectionToSectionDTO(studySection);
                sectionDTOs.add(sectionDTO);
            });
        }
        return sectionDTOs;
    }
}

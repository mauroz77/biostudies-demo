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
            studyDTO.setAccessionID(study.getAccno());
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

    public Study studyDTOToStudy(StudyDTO studyDTO) {
        Study study = new Study();
        if (studyDTO != null) {
            study.setAccno(studyDTO.getAccessionID());
            study.setType(studyDTO.getType());
            study.setAttributes(attributesDTOsToStudyAttributes(study, studyDTO.getAttributes()));
            study.setSection(sectionDTOToStudySection(study, studyDTO.getSection()));
        }

        return study;
    }

    private List<StudyAttribute> attributesDTOsToStudyAttributes(Study study, List<AttributeDTO> attributes) {
        List<StudyAttribute> studyAttributes = new ArrayList<>();
        if (attributes != null) {
            attributes.forEach(attributeDTO -> {
                Attribute attribute = new Attribute();
                attribute.setName(attributeDTO.getName());
                attribute.setValue(attributeDTO.getValue());
                attribute.setReference(attributeDTO.getReference());

                StudyAttribute studyAttribute = new StudyAttribute();
                studyAttribute.setAttribute(attribute);
                studyAttribute.setStudy(study);

                studyAttributes.add(studyAttribute);
            });
        }
        return studyAttributes;
    }

    private StudySection sectionDTOToStudySection(Study study, SectionDTO sectionDTO) {
        StudySection studySection = new StudySection();
        studySection.setAccno(sectionDTO.getAccessionNumber());
        studySection.setType(sectionDTO.getType());
        studySection.setAttributes(attributesDTOsToList(studySection, sectionDTO.getAttributes()));
        studySection.setFiles(filesDTOsToStudyFiles(studySection, sectionDTO.getFiles()));
        studySection.setLinks(linksDTOsToStudyLinkGroups(studySection, sectionDTO.getLinks()));
        studySection.setStudy(study);
        studySection.setSubsections(subsectionDTOsToStudySections(studySection, sectionDTO.getSubsections()));
        return studySection;
    }

    private List<StudySectionAttribute> attributesDTOsToList(StudySection studySection, List<AttributeDTO> attributes) {
        List<StudySectionAttribute> studySectionAttributes = new ArrayList<>();
        if (attributes != null) {
            attributes.forEach(attributeDTO -> {
                Attribute attribute = new Attribute();
                attribute.setName(attributeDTO.getName());
                attribute.setValue(attributeDTO.getValue());
                attribute.setReference(attributeDTO.getReference());

                StudySectionAttribute studySectionAttribute = new StudySectionAttribute();
                studySectionAttribute.setAttribute(attribute);
                studySectionAttribute.setStudySection(studySection);

                studySectionAttributes.add(studySectionAttribute);
            });
        }
        return studySectionAttributes;
    }

    private List<StudyFile> filesDTOsToStudyFiles(StudySection studySection, List<FileDTO> fileDTOS) {
        List<StudyFile> studyFiles = new ArrayList<>();
        if (fileDTOS != null) {
            fileDTOS.forEach(fileDTO -> {
                StudyFile studyFile = new StudyFile();
                studyFile.setPath(fileDTO.getPath());
                studyFile.setSize(fileDTO.getSize());
                studyFile.setType(fileDTO.getType());
                studyFile.setSection(studySection);

                studyFiles.add(studyFile);
            });
        }
        return studyFiles;
    }

    private List<StudyLinkGroup> linksDTOsToStudyLinkGroups(StudySection studySection, List<List<LinkDTO>> listOfLinkDTOS) {
        List<StudyLinkGroup> studyLinkGroups = new ArrayList<>();

        if (listOfLinkDTOS != null) {
            listOfLinkDTOS.forEach(linkDTOS -> {
                List<StudyLink> studyLinks = new ArrayList<>();
                StudyLinkGroup studyLinkGroup = new StudyLinkGroup();
                studyLinkGroup.setSection(studySection);

                linkDTOS.forEach(linkDTO -> {
                    StudyLink studyLink = new StudyLink();
                    studyLink.setUrl(linkDTO.getUrl());
                    studyLink.setAttributes(attributesDTOsToStudyLinkAttributes(studyLink, linkDTO.getAttributes()));
                    studyLink.setLinkGroup(studyLinkGroup);

                    studyLinks.add(studyLink);
                });
                studyLinkGroup.setLinks(studyLinks);
                studyLinkGroups.add(studyLinkGroup);
            });
        }
        return studyLinkGroups;
    }

    private List<StudyLinkAttribute> attributesDTOsToStudyLinkAttributes(StudyLink studyLink, List<AttributeDTO> attributes) {
        List<StudyLinkAttribute> studyLinkAttributes = new ArrayList<>();
        if (attributes != null) {
            attributes.forEach(attributeDTO -> {
                Attribute attribute = new Attribute();
                attribute.setName(attributeDTO.getName());
                attribute.setValue(attributeDTO.getValue());
                attribute.setReference(attributeDTO.getReference());

                StudyLinkAttribute studyLinkAttribute = new StudyLinkAttribute();
                studyLinkAttribute.setAttribute(attribute);
                studyLinkAttribute.setStudyLink(studyLink);

                studyLinkAttributes.add(studyLinkAttribute);

            });
        }
        return studyLinkAttributes;
    }

    private List<StudySection> subsectionDTOsToStudySections(StudySection parentSection, List<SectionDTO> subsectionsDTOs) {
        List<StudySection> subsections = new ArrayList<>();
        if (subsectionsDTOs != null) {
            subsectionsDTOs.forEach(sectionDTO -> {
                StudySection studySection = sectionDTOToStudySection(null, sectionDTO);
                studySection.setParentSection(parentSection);

                subsections.add(studySection);
            });
        }
        return subsections;
    }
}

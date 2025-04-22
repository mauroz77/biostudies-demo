package org.biostudies_demo.api.study.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudySection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accno;
    private String type;

    @OneToMany(mappedBy = "studySection", cascade = CascadeType.ALL)
    private List<StudySectionAttribute> attributes;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<StudyFile> files;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<StudyLinkGroup> links;

    // One-to-one relationship with Study
    @OneToOne
    @JoinColumn(name = "study_id")
    private Study study;

    // Self-referencing relationship for parent-child hierarchy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_section_id")
    private StudySection parentSection;

    // Child sections
    @OneToMany(mappedBy = "parentSection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudySection> subsections = new ArrayList<>();

    // Helper methods to manage the relationship
    public void addSubsection(StudySection subsection) {
        subsections.add(subsection);
        subsection.setParentSection(this);
    }

    public void removeSubsection(StudySection subsection) {
        subsections.remove(subsection);
        subsection.setParentSection(null);
    }

    public String getAbstract() {
        return findAttributeByName("Abstract").getValue();
    }

    private Attribute findAttributeByName(String name) {
        return attributes.stream()
                .map(StudySectionAttribute::getAttribute)
                .filter(attr -> name.equals(attr.getName()))
                .findFirst()
                .orElse(null);
    }
}

package org.biostudies_demo.api.study.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "study")
@Getter
@Setter
@NoArgsConstructor
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accno;

    private String type;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyAttribute> attributes = new ArrayList<>();

    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudySection section;

    @Transient
    public String getTitle() {
        return findAttributeByName("Title").getValue();
    }

    @Transient
    public String getReleaseDate() {
        return findAttributeByName("ReleaseDate").getValue();
    }

    @Transient
    public String getAuthorsAsText() {
        String authorsAsText = "";
        if (section != null && section.getSubsections() != null) {
            authorsAsText  = String.join(" ", section.getSubsections()
                    .stream()
                    .filter(studySection -> "Author".equals(studySection.getType()))
                    .map(StudySection::getName).toList());
        }

        return authorsAsText;
    }

    private Attribute findAttributeByName(String name) {
        return attributes.stream()
                .map(StudyAttribute::getAttribute)
                .filter(attr -> name.equals(attr.getName()))
                .findFirst()
                .orElse(null);
    }

    public void addAttribute(Attribute attribute) {
        StudyAttribute studyAttribute = new StudyAttribute();
        studyAttribute.setStudy(this);
        studyAttribute.setAttribute(attribute);
        this.attributes.add(studyAttribute);
    }

    public void removeAttribute(Attribute attribute) {
        this.attributes.removeIf(sa -> sa.getAttribute().equals(attribute));
    }

    // Helper method to manage the relationship with StudySection
    public void setSection(StudySection section) {
        if (section == null) {
            if (this.section != null) {
                this.section.setStudy(null);
            }
        } else {
            section.setStudy(this);
        }
        this.section = section;
    }
}

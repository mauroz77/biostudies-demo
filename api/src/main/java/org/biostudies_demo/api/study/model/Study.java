package org.biostudies_demo.api.study.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "study")
@Getter
@Setter
@NoArgsConstructor
@Indexed
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accno;

    private String type;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
   // @IndexedEmbedded(includePaths = {"name", "value"})
    private List<StudyAttribute> attributes = new ArrayList<>();

    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudySection section;

//    @FullTextField
//    @GenericField
//    @Transient
//    public String getTitle() {
//        return findAttributeByName("Title").getValue();
//    }

//    @FullTextField(analyzer = "english", name = "abstractText")
//    @GenericField
//    @Transient
//    public String getAbstract() {
//        return findAttributeByName("Abstract").getValue();
//    }

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

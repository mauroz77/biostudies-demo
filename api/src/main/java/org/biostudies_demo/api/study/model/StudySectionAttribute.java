package org.biostudies_demo.api.study.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "study_section_attribute")
@Getter
@Setter
@NoArgsConstructor
public class StudySectionAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_section_id")
    private StudySection studySection;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}

package org.biostudies_demo.api.study.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "study_link_attribute")
@Getter
@Setter
@NoArgsConstructor
public class StudyLinkAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_link_id")
    private StudyLink studyLink;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}

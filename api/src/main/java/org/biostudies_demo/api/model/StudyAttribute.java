package org.biostudies_demo.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "study_attribute")
@Getter
@Setter
@NoArgsConstructor
public class StudyAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}

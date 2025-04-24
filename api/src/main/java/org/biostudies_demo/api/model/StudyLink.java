package org.biostudies_demo.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudyLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "study_link_group_id")
    private StudyLinkGroup linkGroup;

    @OneToMany(mappedBy = "studyLink", cascade = CascadeType.ALL)
    private List<StudyLinkAttribute> attributes;
}

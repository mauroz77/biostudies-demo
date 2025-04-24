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
public class StudyLinkGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private StudySection section;

    @OneToMany(mappedBy = "linkGroup", cascade = CascadeType.ALL)
    private List<StudyLink> links;
}

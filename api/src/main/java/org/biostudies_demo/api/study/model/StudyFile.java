package org.biostudies_demo.api.study.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudyFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;
    private long size;
    private String type;

    @ManyToOne
    @JoinColumn(name = "study_section_id")
    private StudySection section;
}

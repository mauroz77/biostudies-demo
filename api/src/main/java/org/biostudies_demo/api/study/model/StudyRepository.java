package org.biostudies_demo.api.study.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
    Study findByAccno(String accno);
    List<Study> findByAccnoIn(List<String> accnos);
}

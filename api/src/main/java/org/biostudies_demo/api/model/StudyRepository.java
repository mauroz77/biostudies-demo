package org.biostudies_demo.api.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
    Study findByAccno(String accno);
    List<Study> findByAccnoIn(List<String> accnos);
}

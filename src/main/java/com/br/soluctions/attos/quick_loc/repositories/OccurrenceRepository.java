package com.br.soluctions.attos.quick_loc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.soluctions.attos.quick_loc.entities.Occurrence;

import jakarta.transaction.Transactional;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    @Transactional
    @Modifying
    @Query(" update Occurrence o set o.isActive = false where o.occurrenceId = :occurrenceId")
    void occurrenceInactive(@Param("occurrenceId") Long occurrenceId);
}

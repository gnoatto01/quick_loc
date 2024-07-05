package com.br.soluctions.attos.quick_loc.repositories.occurrence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.soluctions.attos.quick_loc.entities.occurrence.Occurrence;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

}

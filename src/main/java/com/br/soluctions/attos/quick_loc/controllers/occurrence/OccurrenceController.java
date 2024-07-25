package com.br.soluctions.attos.quick_loc.controllers.occurrence;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.soluctions.attos.quick_loc.controllers.dto.occurrence.CreateOccurrenceDto;
import com.br.soluctions.attos.quick_loc.entities.occurrence.Occurrence;
import com.br.soluctions.attos.quick_loc.services.occurrence.OccurrenceService;

@RestController
@RequestMapping("/api")
public class OccurrenceController {
    private final OccurrenceService occurrenceService;

    public OccurrenceController(OccurrenceService occurrenceService) {
        this.occurrenceService = occurrenceService;
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/occurrences")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<Occurrence>> listAllOccurrences() {

        var occurrences = occurrenceService.listAllOccurrences();
        return ResponseEntity.ok(occurrences);
    }

    @PostMapping("/occurrences")
    public ResponseEntity<Void> createOccurrence(@RequestBody CreateOccurrenceDto createOccurrenceDto,
            JwtAuthenticationToken token) {

        occurrenceService.createOccurrence(createOccurrenceDto, token);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/occurrences/{id}")
    public ResponseEntity<Void> updateOccurrence(@PathVariable("id") Long occurrenceId,
            @RequestBody CreateOccurrenceDto createOccurrenceDto, JwtAuthenticationToken token) throws Exception {

        occurrenceService.updateOccurrence(occurrenceId, createOccurrenceDto, token);

        return ResponseEntity.ok().build();

    }

    @PutMapping("/occurrences/{id}/inactive")
    public ResponseEntity<Void> occurrenceInactive(@PathVariable("id") Long occurrenceId, JwtAuthenticationToken token)
            throws Exception {
        occurrenceService.occurrenceInactive(occurrenceId, token);

        return ResponseEntity.ok().build();
    }

}

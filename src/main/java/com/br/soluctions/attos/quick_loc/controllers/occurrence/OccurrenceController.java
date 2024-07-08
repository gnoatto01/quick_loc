package com.br.soluctions.attos.quick_loc.controllers.occurrence;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.br.soluctions.attos.quick_loc.controllers.dto.occurrence.CreateOccurrenceDto;
import com.br.soluctions.attos.quick_loc.entities.occurrence.Occurrence;
import com.br.soluctions.attos.quick_loc.repositories.occurrence.OccurrenceRepository;
import com.br.soluctions.attos.quick_loc.repositories.user.UserRepository;

@RestController
public class OccurrenceController {
    private final OccurrenceRepository occurrenceRepository;
    private final UserRepository userRepository;

    public OccurrenceController(OccurrenceRepository occurrenceRepository, UserRepository userRepository) {
        this.occurrenceRepository = occurrenceRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/occurrences")
    public ResponseEntity<Void> createOccurrence(@RequestBody CreateOccurrenceDto createOccurrenceDto,
            JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName())); // pega o subject que foi passado no token

        var occurrence = new Occurrence();

        occurrence.setUser(user.get());
        occurrence.setOcurrenceDescription(createOccurrenceDto.occurrenceContent());
        occurrence.setResponsibleName(createOccurrenceDto.responsibleName());

        occurrenceRepository.save(occurrence);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/occurrences/{id}")
    public ResponseEntity<Void> deleteOccurrence(@PathVariable("id") Long occurrenceId, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var occurrence = occurrenceRepository.findById(occurrenceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); // se nao existir lança excessao

        if (user.get().isAdmin() || occurrence.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            occurrenceRepository.deleteById(occurrenceId);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().build();
    }

}

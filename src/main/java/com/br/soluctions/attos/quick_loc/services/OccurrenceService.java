package com.br.soluctions.attos.quick_loc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.soluctions.attos.quick_loc.controllers.dto.CreateOccurrenceDto;
import com.br.soluctions.attos.quick_loc.entities.Occurrence;
import com.br.soluctions.attos.quick_loc.repositories.OccurrenceRepository;
import com.br.soluctions.attos.quick_loc.repositories.UserRepository;

@Service
public class OccurrenceService {

    private OccurrenceRepository occurrenceRepository;
    private UserRepository userRepository;

    public OccurrenceService(OccurrenceRepository occurrenceRepository, UserRepository userRepository) {
        this.occurrenceRepository = occurrenceRepository;
        this.userRepository = userRepository;
    }

    public Occurrence createOccurrence(CreateOccurrenceDto createOccurrenceDto, JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));
        var occurrence = new Occurrence();

        occurrence.setUser(user.get());
        occurrence.setOcurrenceDescription(createOccurrenceDto.occurrenceDescription());
        occurrence.setWriteLocation(createOccurrenceDto.writeLocation());
        occurrence.setLatitude(createOccurrenceDto.latitude());
        occurrence.setLongitude(createOccurrenceDto.longitude());
        occurrence.setOccurreceStatus(createOccurrenceDto.occurrenceStatus());
        occurrence.setOccurrencePhoto(createOccurrenceDto.occurrencePhoto());
        occurrence.setOccurrencePriority(createOccurrenceDto.occurrencePriority());
        occurrence.setResponsibleName(createOccurrenceDto.responsibleName());
        occurrence.setResolutionDate(createOccurrenceDto.resolutionDate());
        occurrence.setUserContat(createOccurrenceDto.userContat());
        occurrence.setOccurrenceFont(createOccurrenceDto.occurrenceFont());
        occurrence.setActive(createOccurrenceDto.isActive());

        return occurrenceRepository.save(occurrence);
    }

    public Occurrence updateOccurrence(Long id, CreateOccurrenceDto createOccurrenceDto, JwtAuthenticationToken token)
            throws Exception {
        var user = userRepository.findById(UUID.fromString(token.getName()));
        Occurrence occurrence = occurrenceRepository.findById(id).orElseThrow(() -> new Exception("User not found"));

        occurrence.setUser(user.get());
        occurrence.setOcurrenceDescription(createOccurrenceDto.occurrenceDescription());
        occurrence.setWriteLocation(createOccurrenceDto.writeLocation());
        occurrence.setLatitude(createOccurrenceDto.latitude());
        occurrence.setLongitude(createOccurrenceDto.longitude());
        occurrence.setOccurreceStatus(createOccurrenceDto.occurrenceStatus());
        occurrence.setOccurrencePhoto(createOccurrenceDto.occurrencePhoto());
        occurrence.setOccurrencePriority(createOccurrenceDto.occurrencePriority());
        occurrence.setResponsibleName(createOccurrenceDto.responsibleName());
        occurrence.setResolutionDate(createOccurrenceDto.resolutionDate());
        occurrence.setUserContat(createOccurrenceDto.userContat());
        occurrence.setOccurrenceFont(createOccurrenceDto.occurrenceFont());
        occurrence.setActive(createOccurrenceDto.isActive());

        return occurrenceRepository.save(occurrence);
    }

    public void occurrenceInactive(Long occurrenceId, JwtAuthenticationToken token) throws Exception {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var occurrence = occurrenceRepository.findById(occurrenceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); // se nao existir lança excessao

        if (user.get().isAdmin() || occurrence.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            occurrenceRepository.occurrenceInactive(occurrenceId);
        } else {
            throw new Exception("You do not have access to this function");
        }
    }

    public List<Occurrence> listAllOccurrences() {
        List<Occurrence> occurrences = new ArrayList<>();

        occurrences = occurrenceRepository.findAll();

        return occurrences;
    }
}

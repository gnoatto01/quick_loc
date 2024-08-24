package com.br.soluctions.attos.quick_loc.controllers.dto;

import java.sql.Date;

public record CreateOccurrenceDto(String occurrenceDescription, String writeLocation, String latitude, String longitude,
        String occurrenceStatus, String occurrencePhoto, String occurrencePriority, String responsibleName,
        Date resolutionDate, String userContat, String occurrenceFont, boolean isActive) {

}

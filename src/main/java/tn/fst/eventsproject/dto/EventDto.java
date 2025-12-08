package tn.fst.eventsproject.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDto {
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private float cout;
}


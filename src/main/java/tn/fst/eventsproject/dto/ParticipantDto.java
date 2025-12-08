package tn.fst.eventsproject.dto;

import lombok.Data;
import tn.fst.eventsproject.entities.Tache;

@Data
public class ParticipantDto {
    private String nom;
    private String prenom;
    private Tache tache;
}


package tn.fst.eventsproject.dto;

import lombok.Data;

@Data
public class LogisticsDto {
    private String description;
    private boolean reserve;
    private float prixUnit;
    private int quantite;
}


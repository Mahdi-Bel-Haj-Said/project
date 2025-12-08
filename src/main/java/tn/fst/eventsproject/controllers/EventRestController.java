package tn.fst.eventsproject.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.fst.eventsproject.entities.Event;
import tn.fst.eventsproject.entities.Logistics;
import tn.fst.eventsproject.dto.EventDto;
import tn.fst.eventsproject.dto.LogisticsDto;
import tn.fst.eventsproject.dto.ParticipantDto;
import tn.fst.eventsproject.entities.Participant;
import tn.fst.eventsproject.services.IEventServices;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("event")
@RestController
public class EventRestController {
    private final IEventServices eventServices;

    @PostMapping("/addPart")
    public Participant addParticipant(@RequestBody ParticipantDto participantDto){
        Participant participant = mapToParticipant(participantDto);
        return eventServices.addParticipant(participant);
    }
    @PostMapping("/addEvent/{id}")
    public Event addEventPart(@RequestBody EventDto eventDto, @PathVariable("id") int idPart){
        Event event = mapToEvent(eventDto);
        return eventServices.addAffectEvenParticipant(event, idPart);
    }
    @PostMapping("/addEvent")
    public Event addEvent(@RequestBody EventDto eventDto){
        Event event = mapToEvent(eventDto);
        return eventServices.addAffectEvenParticipant(event);
    }
    @PutMapping("/addAffectLog/{description}")
    public Logistics addAffectLog(@RequestBody LogisticsDto logisticsDto, @PathVariable("description") String descriptionEvent){
        Logistics logistics = new Logistics();
        logistics.setDescription(logisticsDto.getDescription());
        logistics.setReserve(logisticsDto.isReserve());
        logistics.setPrixUnit(logisticsDto.getPrixUnit());
        logistics.setQuantite(logisticsDto.getQuantite());
        return eventServices.addAffectLog(logistics,descriptionEvent);
    }
    @GetMapping("/getLogs/{d1}/{d2}")
    public List<Logistics> getLogistiquesDates (@PathVariable("d1") LocalDate date_debut, @PathVariable("d2") LocalDate date_fin){
        return eventServices.getLogisticsDates(date_debut,date_fin);
    }

    private Event mapToEvent(EventDto dto) {
        Event event = new Event();
        event.setDescription(dto.getDescription());
        event.setDateDebut(dto.getDateDebut());
        event.setDateFin(dto.getDateFin());
        event.setCout(dto.getCout());
        return event;
    }

    private Participant mapToParticipant(ParticipantDto dto) {
        Participant participant = new Participant();
        participant.setNom(dto.getNom());
        participant.setPrenom(dto.getPrenom());
        participant.setTache(dto.getTache());
        return participant;
    }
}

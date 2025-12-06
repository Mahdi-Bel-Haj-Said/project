package tn.fst.eventsproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.fst.eventsproject.entities.Event;
import tn.fst.eventsproject.entities.Logistics;
import tn.fst.eventsproject.entities.Participant;
import tn.fst.eventsproject.entities.Tache;
import tn.fst.eventsproject.repositories.EventRepository;
import tn.fst.eventsproject.repositories.LogisticsRepository;
import tn.fst.eventsproject.repositories.ParticipantRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServicesImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private LogisticsRepository logisticsRepository;

    @InjectMocks
    private EventServicesImpl eventServices;

    private Participant participant;
    private Event event;
    private Logistics logistics;

    @BeforeEach
    void setUp() {
        participant = new Participant();
        participant.setIdPart(1);
        participant.setNom("Doe");
        participant.setPrenom("John");
        participant.setTache(Tache.ORGANISATEUR);

        event = new Event();
        event.setIdEvent(1);
        event.setDescription("Concert");
        event.setDateDebut(LocalDate.of(2024, 6, 1));
        event.setDateFin(LocalDate.of(2024, 6, 2));
        event.setCout(0f);

        logistics = new Logistics();
        logistics.setIdLog(1);
        logistics.setDescription("Microphone");
        logistics.setReserve(true);
        logistics.setPrixUnit(100f);
        logistics.setQuantite(2);
    }

    @Test
    void testAddParticipant_ShouldReturnSavedParticipant() {
        // Given
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        // When
        Participant result = eventServices.addParticipant(participant);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getIdPart());
        assertEquals("Doe", result.getNom());
        assertEquals("John", result.getPrenom());
        verify(participantRepository, times(1)).save(participant);
    }

    @Test
    void testAddAffectEvenParticipant_WithIdParticipant_ShouldAddEventToParticipant() {
        // Given
        int idParticipant = 1;
        when(participantRepository.findById(idParticipant)).thenReturn(Optional.of(participant));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // When
        Event result = eventServices.addAffectEvenParticipant(event, idParticipant);

        // Then
        assertNotNull(result);
        assertTrue(participant.getEvents().contains(event));
        verify(participantRepository, times(1)).findById(idParticipant);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testGetLogisticsDates_ShouldReturnReservedLogistics() {
        // Given
        LocalDate dateDebut = LocalDate.of(2024, 6, 1);
        LocalDate dateFin = LocalDate.of(2024, 6, 30);

        Set<Logistics> logisticsSet = new HashSet<>();
        logisticsSet.add(logistics);

        Logistics logisticsNotReserved = new Logistics();
        logisticsNotReserved.setIdLog(2);
        logisticsNotReserved.setDescription("Table");
        logisticsNotReserved.setReserve(false);
        logisticsNotReserved.setPrixUnit(50f);
        logisticsNotReserved.setQuantite(5);
        logisticsSet.add(logisticsNotReserved);

        event.setLogistics(logisticsSet);
        List<Event> events = Arrays.asList(event);

        when(eventRepository.findByDateDebutBetween(dateDebut, dateFin)).thenReturn(events);

        // When
        List<Logistics> result = eventServices.getLogisticsDates(dateDebut, dateFin);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(logistics));
        assertTrue(result.get(0).isReserve());
        verify(eventRepository, times(1)).findByDateDebutBetween(dateDebut, dateFin);
    }
}


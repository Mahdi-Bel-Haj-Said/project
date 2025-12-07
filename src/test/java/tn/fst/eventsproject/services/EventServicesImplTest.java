package tn.fst.eventsproject.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.fst.eventsproject.entities.Event;
import tn.fst.eventsproject.entities.Participant;
import tn.fst.eventsproject.repositories.EventRepository;
import tn.fst.eventsproject.repositories.LogisticsRepository;
import tn.fst.eventsproject.repositories.ParticipantRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @Test
    void testAddParticipant_Simple() {
        // Arrange
        Participant participant = new Participant();
        participant.setIdPart(1);
        participant.setNom("Test");
        
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        // Act
        Participant result = eventServices.addParticipant(participant);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdPart());
    }

    @Test
    void testAddAffectEvenParticipant_Simple() {
        // Arrange
        Event event = new Event();
        event.setIdEvent(1);
        event.setDescription("Test Event");
        
        Participant participant = new Participant();
        participant.setIdPart(1);
        
        when(participantRepository.findById(1)).thenReturn(Optional.of(participant));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Act
        Event result = eventServices.addAffectEvenParticipant(event, 1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdEvent());
    }

}


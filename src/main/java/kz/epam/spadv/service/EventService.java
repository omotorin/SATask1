package kz.epam.spadv.service;

import kz.epam.spadv.domain.Auditorium;
import kz.epam.spadv.domain.Event;
import kz.epam.spadv.service.exception.AuditoriumAlreadyAssignedException;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
public interface EventService {
    Event create(String name, float price, Rating rating);

    Event create(String name, LocalDateTime dateTime, float price, Rating rating);

    void remove(Event event);
    Event getByName(String name);

    Event getById(long id);

    Collection<Event> getAll();
    Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to);
    Collection<Event> getNextEvents(LocalDateTime to);
    Event assignAuditorium(Event event, Auditorium auditorium, LocalDateTime dateTime) throws
            AuditoriumAlreadyAssignedException;
}

package kz.epam.spadv.service;

import kz.epam.spadv.domain.Auditorium;
import kz.epam.spadv.domain.Event;
import kz.epam.spadv.repository.EventRepository;
import kz.epam.spadv.service.exception.AuditoriumAlreadyAssignedException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
public class EventServiceImpl implements EventService {

    private static final Logger log = Logger.getLogger(EventService.class);

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event create(String name, float price, Rating rating) {
        Event event = new Event(name, null, price, rating);
        log.info("Event <" + event.getName() + "> created");
        return eventRepository.save(event);
    }

    @Override
    public Event create(String name, LocalDateTime dateTime, float price, Rating rating) {
        Event event = new Event(name, dateTime, price, rating);
        log.info("Event <" + event.getName() + "> created");
        return eventRepository.save(event);
    }

    @Override
    public void remove(Event event) {
        if (event != null && event.getId()!=null) {
            eventRepository.delete(event.getId());
        }
    }

    @Override
    public Event getByName(String name) {
        return eventRepository.getByName(name);
    }

    @Override
    public Event getById(long id) {
        return eventRepository.getById(id);
    }

    @Override
    public Collection<Event> getAll() {
        return eventRepository.getAll();
    }

    @Override
    public Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        Collection<Event> events = eventRepository.getAll();
        return events.stream()
                .filter(e -> e.getDateTime().isAfter(from) && e.getDateTime().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Event> getNextEvents(LocalDateTime to) {
        return getForDateRange(LocalDateTime.now(), to);
    }

    @Override
    public Event assignAuditorium(Event event, Auditorium auditorium, LocalDateTime dateTime) throws AuditoriumAlreadyAssignedException {
        if (event != null && auditorium != null && dateTime != null) {
            long count = eventRepository.getAll().stream()
                    .filter(e -> e.getDateTime() != null && e.getDateTime().isEqual(dateTime))
                    .map(Event::getAuditorium)
                    .filter(a -> a!=null)
                    .filter(a -> a.getId() == auditorium.getId())
                    .count();
            if (count != 0) {
                throw new AuditoriumAlreadyAssignedException(event, auditorium);
            }
            event.setAuditorium(auditorium);
            event.setDateTime(dateTime);
            eventRepository.save(event);

            log.info("Event <" + event.getName() + "> assigned to auditorium <" + auditorium.getName() + ">");
        }
        return event;
    }

}

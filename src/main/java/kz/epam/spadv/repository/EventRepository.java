package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Event;

import java.util.Collection;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
public interface EventRepository {
    Event save(Event event);
    void delete(long id);
    Event getByName(String name);

    Event getById(Long id);

    Collection<Event> getAll();
}

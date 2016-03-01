package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
public class MapEventRepository implements EventRepository {

    private Map<Long, Event> events = new HashMap<Long, Event>();
    private long id = 0L;

    @Override
    public Event save(Event event) {
        if (event == null) return null;
        if (event.getId() == null) {
            id++;
            event.setId(id);
        }
        events.put(event.getId(), event);
        return event;
    }

    @Override
    public void delete(long id) {
        events.remove(id);
    }

    @Override
    public Event getByName(String name) {
        return events.values().stream()
                .filter(event -> event.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override public Event getById(Long id) {
        return events.values().stream()
                .filter(event -> event.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public Collection<Event> getAll() {
        return events.values();
    }
}

package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Auditorium;
import kz.epam.spadv.domain.Event;
import kz.epam.spadv.service.Rating;
import kz.epam.spadv.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg_Motorin on 28.10.2015.
 */
public class JdbcEventRepository implements EventRepository {

    private static final String UPDATE_BY_ID = "UPDATE event SET name=?, date=?, ticketPrice=?, rating=?, auditorium_id=? WHERE id=?";
    private static final String UPDATE_BY_NAME = "UPDATE event SET name=?, date=?, ticketPrice=?, rating=?, auditorium_id=? WHERE name=?";
    private static final String DELETE_BOOKED_TICKETS = "DELETE FROM tickets WHERE ticket_id IN (SELECT t.id FROM ticket t WHERE t.event_id=?)";
    private static final String DELETE_TICKETS_BY_EVENT_ID = "DELETE FROM ticket WHERE event_id=?";
    private static final String DELETE_EVENT = "DELETE FROM event WHERE id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM event WHERE id=?";
    private static final String SELECT_BY_NAME = "SELECT * FROM event WHERE name=?";
    private static final String SELECT_ALL = "SELECT * FROM event";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AuditoriumRepository auditoriumRepository;

    @Override
    public Event save(Event event) {
        if (event != null) {
            int updatedRow = 0;
            if (event.getId() != null) {
                updatedRow = jdbcTemplate.update(UPDATE_BY_ID,
                        event.getName(),
                        Convert.toTimestamp(event.getDateTime()),
                        event.getTicketPrice(),
                        event.getRating().getValue(),
                        event.getAuditorium() != null ? event.getAuditorium().getId() : null,
                        event.getId());
                if(updatedRow!=0) {
                    event = getById(event.getId());
                }
            } else if (event.getName() != null) {
                updatedRow = jdbcTemplate.update(UPDATE_BY_NAME,
                        event.getName(),
                        Convert.toTimestamp(event.getDateTime()),
                        event.getTicketPrice(),
                        event.getRating().getValue(),
                        event.getAuditorium() != null ? event.getAuditorium().getId() : null,
                        event.getName());
                if(updatedRow!=0) {
                    event = getByName(event.getName());
                }
            }
            if (updatedRow == 0) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("event");
                insert.setGeneratedKeyName("id");
                Map<String, Object> args = new HashMap<>();
                args.put("name", event.getName());
                args.put("date", Convert.toTimestamp(event.getDateTime()));
                args.put("ticketPrice", event.getTicketPrice());
                args.put("rating", event.getRating().getValue());
                args.put("auditorium_id", event.getAuditorium() != null ? event.getAuditorium().getId() : null);
                event.setId(insert.executeAndReturnKey(args).longValue());
            }
        }
        return event;
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE_BOOKED_TICKETS, id);
        jdbcTemplate.update(DELETE_TICKETS_BY_EVENT_ID, id);
        jdbcTemplate.update(DELETE_EVENT, id);
    }

    @Override
    public Event getByName(String name) {
        Event event = null;
        if (name != null) {
            try {
                event = jdbcTemplate.queryForObject(SELECT_BY_NAME, new EventMapper(), name);
                event.setAuditorium(event.getAuditorium().getId() != 0 ?
                        auditoriumRepository.getById(event.getAuditorium().getId()) : null);
            } catch (EmptyResultDataAccessException ex){
                event = null;
            }
        }
        return event;
    }

    @Override
    public Event getById(Long id) {
        Event event = null;
        if (id != null) {
            try {
                event = jdbcTemplate.queryForObject(SELECT_BY_ID, new EventMapper(), id);
                event.setAuditorium(event.getAuditorium().getId() != 0 ?
                        auditoriumRepository.getById(event.getAuditorium().getId()) : null);
            } catch (EmptyResultDataAccessException ex){
                event = null;
            }
        }
        return event;
    }

    private Collection<Event> setAuditorium(Collection<Event> events) {
        events.stream().forEach(e ->
                        e.setAuditorium(e.getAuditorium().getId() != 0 ?
                                auditoriumRepository.getById(e.getAuditorium().getId()) : null)
        );
        return events;
    }

    @Override
    public Collection<Event> getAll() {
        Collection<Event> events = jdbcTemplate.query(SELECT_ALL, new EventMapper());
        events = setAuditorium(events);
        return events;
    }

    private static final class EventMapper implements RowMapper<Event> {

        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Timestamp timestamp = rs.getTimestamp(3);
            Event e = new Event(
                    rs.getLong(1),
                    rs.getString(2),
                    timestamp != null ? timestamp.toLocalDateTime() : null,
                    rs.getFloat(4),
                    Rating.getRating(rs.getInt(5))
            );
            Auditorium a = new Auditorium(rs.getInt(6));
            e.setAuditorium(a);

            return e;
        }
    }

}

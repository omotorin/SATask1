package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Event;
import kz.epam.spadv.service.Rating;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Collection;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-map.xml")
public class MapEventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void shouldSave() throws Exception {
        Event event = new Event("name", LocalDateTime.now(), 1000F, Rating.HIGH);
        eventRepository.save(event);
        assertThat(eventRepository.getByName("name"), is(event));
    }

    @Test
    public void shouldDelete() throws Exception {
        Event event = new Event("name", LocalDateTime.now(), 1000F, Rating.HIGH);
        eventRepository.save(event);
        eventRepository.delete(event.getId());
        assertNull(eventRepository.getByName("name"));
    }

    @Test
    public void shouldGetByName() throws Exception {
        Event event1 = new Event("name1", LocalDateTime.now(), 1000F, Rating.HIGH);
        Event event2 = new Event("name2", LocalDateTime.now(), 1000F, Rating.HIGH);
        eventRepository.save(event1);
        eventRepository.save(event2);
        assertThat(eventRepository.getByName("name1"), is(event1));
    }

    public void shouldReturnNullIfNotFoundedByName() throws Exception {
        Event actualEvent = eventRepository.getByName("event3");
        assertEquals(null, actualEvent);
    }

    @Test
    public void shouldGetAll() throws Exception {
        Event event1 = new Event("name1", LocalDateTime.now(), 1000F, Rating.HIGH);
        Event event2 = new Event("name2", LocalDateTime.now(), 1000F, Rating.HIGH);
        eventRepository.save(event1);
        eventRepository.save(event2);
        Collection<Event> result = eventRepository.getAll();

        assertTrue(result.size() == 2);
        assertTrue(result.contains(event1));
        assertTrue(result.contains(event2));
    }

    @After
    public void tearDown() throws Exception {
        eventRepository.getAll().clear();
    }
}

package kz.epam.spadv.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import kz.epam.spadv.domain.Event;
import kz.epam.spadv.service.Rating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDateTime;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Oleg_Motorin on 05.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-jdbc.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, ForeignKeyDisabling.class})
public class JdbcEventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AuditoriumRepository auditoriumRepository;

    @Test
    @DatabaseSetup("classpath:/data/initEvents.xml")
    @ExpectedDatabase("classpath:/data/eventSaved.xml")
    public void shouldSave() throws Exception {
        LocalDateTime dateTime = LocalDateTime.parse("2015-12-01T00:00");
        Event event1 = new Event("event1", dateTime, 100F, Rating.MID);
        Event event3 = new Event("event3", null, 100F, Rating.MID);
        event1.setAuditorium(auditoriumRepository.getById(1));
        eventRepository.save(event1);
        eventRepository.save(event3);
    }

    @Test
    @DatabaseSetup("classpath:/data/initEvents.xml")
    @ExpectedDatabase("classpath:/data/eventDeleted.xml")
    public void shouldDelete() throws Exception {
        eventRepository.delete(0);
        eventRepository.delete(1);
    }

    @Test
    @DatabaseSetup("classpath:/data/initEvents.xml")
    public void shouldGetByName() throws Exception {
        Event expectedEvent = new Event(0, "event1", null, 100F, Rating.MID);
        Event actualEvent = eventRepository.getByName("event1");
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    @DatabaseSetup("classpath:/data/initEvents.xml")
    public void shouldReturnNullIfNotFoundedByName() throws Exception {
        Event actualEvent = eventRepository.getByName("event3");
        assertEquals(null, actualEvent);
    }

    @Test
    @DatabaseSetup("classpath:/data/initEvents.xml")
    public void shouldGetAll() throws Exception {
        Collection<Event> events = eventRepository.getAll();
        assertEquals(2, events.size());
    }

}

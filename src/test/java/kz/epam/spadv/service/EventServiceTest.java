package kz.epam.spadv.service;

import kz.epam.spadv.domain.Auditorium;
import kz.epam.spadv.domain.Event;
import kz.epam.spadv.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by ���� on 22.10.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-map.xml")
public class EventServiceTest {

    private LocalDateTime parseDate(String date){
        return LocalDateTime.parse(date);
    }

    @Test
    public void testCreate() throws Exception {
        Event event = new Event(1L, "name", null, 100F, Rating.HIGH);
        EventRepository mockRepo = mock(EventRepository.class);
        EventService service = new EventServiceImpl(mockRepo);
        service.create("name", 100F, Rating.HIGH);
        when(mockRepo.save(anyObject())).thenReturn(event);

        verify(mockRepo).save(anyObject());
    }

    @Test
    public void testRemove() throws Exception {
        Event event = new Event(1, "name", parseDate("2015-01-01T10:30"), 100F, Rating.HIGH);
        EventRepository mockRepo = mock(EventRepository.class);

        EventService service = new EventServiceImpl(mockRepo);
        service.remove(event);

        verify(mockRepo).delete(event.getId());
    }

    @Test
    public void testGetByName() throws Exception {
        Event event = new Event("name", parseDate("2015-01-01T10:30"), 100F, Rating.HIGH);
        EventRepository mockRepo = mock(EventRepository.class);
        when(mockRepo.getByName("name")).thenReturn(event);

        EventService service = new EventServiceImpl(mockRepo);
        Event result = service.getByName("name");

        assertThat(result, is(event));

        verify(mockRepo).getByName("name");
    }

    @Test
    public void testGetAll() throws Exception {
        Event event = new Event("name", parseDate("2015-01-01T10:30"), 100F, Rating.HIGH);
        EventRepository mockRepo = mock(EventRepository.class);
        when(mockRepo.getAll()).thenReturn(Arrays.asList(event));

        EventService service = new EventServiceImpl(mockRepo);
        Collection<Event> result = service.getAll();

        assertThat(result, is(Arrays.asList(event)));

        verify(mockRepo).getAll();
    }

    @Test
    public void shouldGetForDateRange() throws Exception {
        Event event = new Event("name", parseDate("2015-01-01T10:30"), 100F, Rating.HIGH);
        EventRepository mockRepo = mock(EventRepository.class);
        when(mockRepo.getAll()).thenReturn(Arrays.asList(event));

        EventService service = new EventServiceImpl(mockRepo);
        Collection<Event> result = service.getForDateRange(parseDate("2015-01-01T09:00"),
                parseDate("2015-01-01T10:40"));

        assertThat(result, is(Arrays.asList(event)));

        verify(mockRepo).getAll();
    }

    @Test
    public void shouldNotGetIfOutOfDateRange() throws Exception {
        Event event = new Event("name", parseDate("2015-01-01T10:30"), 100F, Rating.HIGH);
        EventRepository mockRepo = mock(EventRepository.class);
        when(mockRepo.getAll()).thenReturn(Arrays.asList(event));

        EventService service = new EventServiceImpl(mockRepo);
        Collection<Event> result = service.getForDateRange(parseDate("2015-01-01T11:00"),
                parseDate("2015-01-01T12:40"));

        assertTrue(result.isEmpty());

        verify(mockRepo).getAll();
    }

    @Test
    public void shouldGetNextEvents() throws Exception {
        Event event = new Event("name", parseDate("2016-03-18T10:30"), 100F, Rating.HIGH);
        EventRepository mockRepo = mock(EventRepository.class);
        when(mockRepo.getAll()).thenReturn(Arrays.asList(event));

        EventService service = new EventServiceImpl(mockRepo);
        Collection<Event> result = service.getNextEvents(parseDate("2016-11-01T00:00"));

        assertThat(result, is(Arrays.asList(event)));

        verify(mockRepo).getAll();
    }

    @Test
    public void shouldNotGetNextEventsIfOutOfDate() throws Exception {
        Event event = new Event("name", parseDate("2015-12-01T10:30"), 100F, Rating.HIGH);
        EventRepository mockRepo = mock(EventRepository.class);
        when(mockRepo.getAll()).thenReturn(Arrays.asList(event));

        EventService service = new EventServiceImpl(mockRepo);
        Collection<Event> result = service.getNextEvents(parseDate("2015-11-01T00:00"));

        assertTrue(result.isEmpty());

        verify(mockRepo).getAll();
    }

    @Test
    public void shouldAssignAuditorium() throws Exception {
        Event event = new Event("name", parseDate("2015-12-01T10:30"), 100F, Rating.HIGH);
        Auditorium auditorium = new Auditorium(1, "red", 100, "10,11,12,13");
        event.setAuditorium(auditorium);

        EventRepository mockRepo = mock(EventRepository.class);
        when(mockRepo.getAll()).thenReturn(Arrays.asList(event));
        when(mockRepo.save(event)).thenReturn(event);

        EventService service = new EventServiceImpl(mockRepo);
        Event result = service.assignAuditorium(event, auditorium, parseDate("2015-11-01T00:00"));

        assertThat(result.getAuditorium(), is(auditorium));

        verify(mockRepo).getAll();
        verify(mockRepo).save(event);
    }

    @Test
    public void shouldNotAssignAuditoriumIfAnotherAlreadyAssigned() {
        Event event1 = new Event("name1", parseDate("2015-12-01T10:30"), 100F, Rating.HIGH);
        Event event2 = new Event("name2", null, 100F, Rating.HIGH);
        Auditorium auditorium = new Auditorium(1, "red", 100, "10,11,12,13");
        event1.setAuditorium(auditorium);
        event2.setAuditorium(auditorium);

        EventRepository mockRepo = mock(EventRepository.class);
        when(mockRepo.getAll()).thenReturn(Arrays.asList(event1));
        when(mockRepo.save(event1)).thenReturn(event1);

        EventService service = new EventServiceImpl(mockRepo);
        try{
            service.assignAuditorium(event1, auditorium, parseDate("2015-11-01T00:00"));
            service.assignAuditorium(event2, auditorium, parseDate("2015-11-01T00:00"));
            fail();
        } catch (Exception e) {
            verify(mockRepo, never()).save(event2);
        }
    }
}

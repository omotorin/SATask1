package kz.epam.spadv.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import kz.epam.spadv.domain.Event;
import kz.epam.spadv.domain.Seat;
import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.service.Rating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Oleg_Motorin on 06.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-jdbc.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, ForeignKeyDisabling.class})
public class JdbcTicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    @ExpectedDatabase("classpath:/data/ticketSaved.xml")
    public void shouldSave() throws Exception {
        Ticket ticket = new Ticket();
        Event event = new Event(1, "event1", null, 100F, Rating.MID);
        ticket.setEvent(event);
        ticket.setSeat(new Seat(4));
        ticket.setPrice(100);
        ticketRepository.save(ticket);
    }

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    public void shouldGetAll() throws Exception {
        Collection<Ticket> tickets = ticketRepository.getAll();
        assertEquals(3, tickets.size());
    }

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    public void shouldGetByEventName() throws Exception {
        Collection<Ticket> tickets = ticketRepository.getByEventName("event1");
        assertEquals(2, tickets.size());
        tickets.forEach(t -> assertEquals("event1", t.getEvent().getName()));
    }

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    public void shouldReturnEmptyIfNotFoundedByEventName() throws Exception {
        Collection<Ticket> tickets = ticketRepository.getByEventName("event3");
        assertEquals(0, tickets.size());
    }

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    public void shouldGetBookedTickets() throws Exception {
        Collection<Ticket> tickets = ticketRepository.getBookedTickets();
        assertEquals(3, tickets.size());
    }

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    public void shouldGetBookedTicketsByUserId() throws Exception {
        Collection<Ticket> tickets = ticketRepository.getBookedTicketsByUserId(0);
        assertEquals(2, tickets.size());
        assertEquals(tickets.stream().map(Ticket::getId).collect(Collectors.toList()),
                Arrays.asList(0L, 1L));
    }

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    public void shouldReturnEmptyIfUserNotBookedTickets() throws Exception {
        Collection<Ticket> tickets = ticketRepository.getBookedTicketsByUserId(3);
        assertEquals(0, tickets.size());
    }

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    @ExpectedDatabase("classpath:/data/ticketBooked.xml")
    public void shouldSaveBookedTicket() throws Exception {
        Ticket ticket = new Ticket();
        Event event = new Event(1, "event2", null, 100F, Rating.MID);
        ticket.setId(2);
        ticket.setEvent(event);
        ticket.setSeat(new Seat(3));
        ticket.setPrice(100);
        User user = new User(0, "user1", "email1");
        ticketRepository.saveBookedTicket(user, ticket);
    }

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    @ExpectedDatabase("classpath:/data/ticketDeleteBooked.xml")
    public void shouldDeleteBookedTicketByUserId() throws Exception{
        ticketRepository.deleteBookedTicketByUserId(0);
    }

    @Test
    @DatabaseSetup("classpath:/data/initTickets.xml")
    @ExpectedDatabase("classpath:/data/initTickets.xml")
    public void shouldNotDeleteBookedTicketIfUserNotBooked() throws Exception{
        ticketRepository.deleteBookedTicketByUserId(3);
    }
}

package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Event;
import kz.epam.spadv.domain.Seat;
import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.service.Rating;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Oleg_Motorin on 09.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-map.xml")
public class MapTicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void shouldSave() throws Exception {
        Ticket ticket = new Ticket();
        Event event = new Event("event1", null, 100F, Rating.MID);
        ticket.setEvent(event);
        ticket.setSeat(new Seat(4));
        ticket.setPrice(100);
        Ticket result = ticketRepository.save(ticket);
        assertNotNull(result);
        assertEquals(1, result.getId().longValue());
    }

    @Test
    public void shouldGetAll() throws Exception {
        ticketRepository.save(new Ticket());
        ticketRepository.save(new Ticket());
        Collection<Ticket> tickets = ticketRepository.getAll();
        assertEquals(2, tickets.size());
    }

    @Test
    public void shouldGetByEventName() throws Exception {
        Event event1 = new Event("event1", null, 100F, Rating.MID);
        Event event2 = new Event("event2", null, 100F, Rating.MID);
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        ticket1.setEvent(event1);
        ticket2.setEvent(event1);
        ticket3.setEvent(event2);
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
        Collection<Ticket> tickets = ticketRepository.getByEventName("event1");
        assertEquals(2, tickets.size());
        tickets.forEach(t -> t.getEvent().getName().equals("event1"));
    }

    @Test
    public void shouldReturnEmptyIfNotFoundedByEventName() throws Exception {
        Event event1 = new Event("event1", null, 100F, Rating.MID);
        Event event2 = new Event("event2", null, 100F, Rating.MID);
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        ticket1.setEvent(event1);
        ticket2.setEvent(event1);
        ticket3.setEvent(event2);
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
        Collection<Ticket> tickets = ticketRepository.getByEventName("event3");
        assertEquals(0, tickets.size());
    }

    @Test
    public void shouldGetBookedTickets() throws Exception {
        User user = new User(1, "user1", "email1");
        Ticket ticket = new Ticket();
        ticketRepository.save(ticket);
        ticketRepository.saveBookedTicket(user, ticket);
        Collection<Ticket> bookedTickets = ticketRepository.getBookedTickets();
        assertEquals(1, bookedTickets.size());
        bookedTickets.forEach(t -> assertEquals(ticket, t));
    }

    @Test
    public void shouldGetBookedTicketsByUserId() throws Exception {
        User user1 = new User(1, "user1", "email1");
        User user2 = new User(2, "user2", "email2");
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
        ticketRepository.saveBookedTicket(user1, ticket1);
        ticketRepository.saveBookedTicket(user1, ticket2);
        ticketRepository.saveBookedTicket(user2, ticket3);
        Collection<Ticket> tickets = ticketRepository.getBookedTicketsByUserId(1);
        assertEquals(2, tickets.size());
        assertEquals(tickets.stream().map(Ticket::getId).collect(Collectors.toList()),
                Arrays.asList(1L, 2L));
    }

    @Test
    public void shouldReturnEmptyIfUserNotBookedTickets() throws Exception {
        User user1 = new User(1, "user1", "email1");
        User user2 = new User(2, "user2", "email2");
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
        ticketRepository.saveBookedTicket(user1, ticket1);
        ticketRepository.saveBookedTicket(user1, ticket2);
        ticketRepository.saveBookedTicket(user2, ticket3);
        Collection<Ticket> tickets = ticketRepository.getBookedTicketsByUserId(3);
        assertEquals(0, tickets.size());
    }

    @Test
    public void shouldSaveBookedTicket() throws Exception {
        User user = new User(1, "user1", "email1");
        Ticket ticket = new Ticket();
        ticketRepository.save(ticket);
        ticketRepository.saveBookedTicket(user, ticket);
        Collection<Ticket> bookedTickets = ticketRepository.getBookedTickets();
        assertEquals(1, bookedTickets.size());
        bookedTickets.forEach(t -> assertEquals(ticket, t));
    }

    @After
    public void tearDown() throws Exception {
        ((MapTicketRepository)ticketRepository).cleanUp();
    }

    @Test
    public void shouldDeleteBookedTicketByUserId() throws Exception {
        User user = new User(1, "user1", "email1");
        Ticket ticket = new Ticket();
        ticketRepository.save(ticket);
        ticketRepository.saveBookedTicket(user, ticket);
        ticketRepository.deleteBookedTicketByUserId(1);
        Collection<Ticket> bookedTickets = ticketRepository.getBookedTickets();
        assertEquals(0, bookedTickets.size());
    }
}

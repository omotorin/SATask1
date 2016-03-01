package kz.epam.spadv.service;

import kz.epam.spadv.domain.*;
import kz.epam.spadv.repository.AuditoriumRepository;
import kz.epam.spadv.repository.TicketRepository;
import kz.epam.spadv.service.exception.TicketAlreadyBookedException;
import kz.epam.spadv.service.exception.TicketWithoutEventException;
import kz.epam.spadv.service.exception.UserNotRegisteredException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
/**
 * Created by ���� on 22.10.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private EventService eventService;
    @Mock
    private DiscountService discountService;
    @Mock
    private AuditoriumRepository auditoriumRepository;
    private BookingServiceImpl service;
    private User user;
    private Event event;
    private Ticket ticket;
    private Auditorium auditorium;

    @Before
    public void initMocks() {
        service = new BookingServiceImpl();
        service.setUserService(userService);
        service.setTicketRepository(ticketRepository);
        service.setEventService(eventService);
        service.setDiscountService(discountService);
        service.setAuditoriumRepository(auditoriumRepository);
        user = new User("user1", "email1");
        event = new Event("event1", null, 100F, Rating.MID);
        auditorium = new Auditorium(1, "red", 100, "10,11,12,13");
        event.setAuditorium(auditorium);
        ticket = new Ticket();
        ticket.setSeat(new Seat(1));
        ticket.setEvent(event);
        ticket.setPrice(event.getTicketPrice());
    }

    @Test
    public void shouldReturnTicketPriceIfNoDiscount() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        event.setDateTime(now);
        ticketRepository.save(ticket);
        given(eventService.getByName(event.getName())).willReturn(event);
        given(userService.getUserByName(user.getName())).willReturn(user);
        given(discountService.getDiscount(user, event, now)).willReturn(0F);
        given(auditoriumRepository.getSeatByAuditoriumIdAndNumber(auditorium.getId(), 1))
                .willReturn(new Seat(1));

        float price = service.getTicketPrice(event, now, Arrays.asList(1), user);

        assertEquals(price, event.getTicketPrice());
    }

    @Test
    public void shouldBookTicket() throws Exception {
        when(userService.getUserByName(user.getName())).thenReturn(user);
        when(ticketRepository.getBookedTickets()).thenReturn(Arrays.asList(ticket));

        Ticket newTicket = new Ticket();
        newTicket.setEvent(event);
        newTicket.setSeat(new Seat(2));
        service.bookTicket(user, newTicket);

        verify(userService).getUserByName(user.getName());
        verify(ticketRepository).getBookedTickets();
        verify(ticketRepository).saveBookedTicket(user, newTicket);
    }

    @Test
    public void shouldNotBookTicketIfBooked() throws TicketWithoutEventException {
        when(userService.getUserByName(user.getName())).thenReturn(user);
        when(ticketRepository.getBookedTickets()).thenReturn(Arrays.asList(ticket));
        doThrow(TicketAlreadyBookedException.class).when(ticketRepository).saveBookedTicket(user, ticket);

        try {
            service.bookTicket(user, ticket);
            fail();
        } catch (UserNotRegisteredException | TicketAlreadyBookedException e) {
            verify(ticketRepository).getBookedTickets();
            verify(ticketRepository, never()).saveBookedTicket(user, ticket);
        }

        verify(userService).getUserByName(user.getName());

    }

    @Test
    public void shouldNotBookTicketIfUserNotRegistered() throws TicketWithoutEventException {
        try {
            service.bookTicket(user, ticket);
            fail();
        } catch (UserNotRegisteredException | TicketAlreadyBookedException e) {
            verify(ticketRepository, never()).getBookedTickets();
            verify(ticketRepository, never()).saveBookedTicket(user, ticket);
        }

        verify(userService).getUserByName(user.getName());

    }
}

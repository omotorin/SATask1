package kz.epam.spadv.service;

import kz.epam.spadv.domain.Event;
import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.service.exception.EventNotAssignedException;
import kz.epam.spadv.service.exception.TicketAlreadyBookedException;
import kz.epam.spadv.service.exception.TicketWithoutEventException;
import kz.epam.spadv.service.exception.UserNotRegisteredException;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by ���� on 22.10.2015.
 */
public interface BookingService {
    float getTicketPrice(Event event, LocalDateTime dateTime, Collection<Integer> seatNumbers, User user) throws UserNotRegisteredException, EventNotAssignedException;

    void bookTicket(User user, Ticket ticket) throws UserNotRegisteredException, TicketAlreadyBookedException, TicketWithoutEventException;

    Collection<Ticket> getTicketsForEvent(Event event, LocalDateTime dateTime);
}

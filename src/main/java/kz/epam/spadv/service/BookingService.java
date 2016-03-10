package kz.epam.spadv.service;

import kz.epam.spadv.domain.Event;
import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.repository.exception.AccountNotFoundException;
import kz.epam.spadv.service.exception.*;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by ���� on 22.10.2015.
 */
public interface BookingService {
    float getTicketPrice(Event event, LocalDateTime dateTime, Collection<Integer> seatNumbers, User user) throws UserNotRegisteredException, EventNotAssignedException;

    void bookTicket(User user, Ticket ticket) throws UserNotRegisteredException, TicketAlreadyBookedException, TicketWithoutEventException, NotEnoughMoneyForWithdrawal, AccountNotFoundException;

    Collection<Ticket> getTicketsForEvent(Event event, LocalDateTime dateTime);
}

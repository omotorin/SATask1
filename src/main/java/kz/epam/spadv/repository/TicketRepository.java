package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;

import java.util.Collection;

/**
 * Created by ���� on 22.10.2015.
 */
public interface TicketRepository {
    Ticket save(Ticket ticket);
    Collection<Ticket> getAll();
    Collection<Ticket> getByEventName(String eventName);
    Collection<Ticket> getBookedTickets();
    Collection<Ticket> getBookedTicketsByUserId(long userId);
    void saveBookedTicket(User user, Ticket ticket);
    void deleteBookedTicketByUserId(long userId);
}

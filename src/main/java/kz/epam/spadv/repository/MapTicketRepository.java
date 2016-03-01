package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ���� on 22.10.2015.
 */
public class MapTicketRepository implements TicketRepository {

    private long id = 0L;
    private Map<Long, Ticket> tickets = new HashMap<>();
    private Map<Long, User> bookedTickets = new HashMap<>();

    @Override
    public Ticket save(Ticket ticket) {
        if (ticket != null) {
            if (ticket.getId() == null) {
                id++;
                ticket.setId(id);
            }
            tickets.put(ticket.getId(), ticket);
        }
        return ticket;
    }

    @Override
    public Collection<Ticket> getAll() {
        return tickets.values();
    }

    @Override
    public Collection<Ticket> getByEventName(String eventName) {
        return tickets.values().stream()
                .filter(t -> t.getEvent().getName().equals(eventName))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Ticket> getBookedTickets() {
        Collection<Ticket> ticketList = tickets.values().stream()
                .filter(t -> bookedTickets.containsKey(t.getId()))
                .collect(Collectors.toList());

        return ticketList;
    }

    @Override
    public Collection<Ticket> getBookedTicketsByUserId(long userId) {

        Collection<Ticket> ticketList = new ArrayList<>();

        bookedTickets.entrySet().stream()
                .filter(entry -> entry.getValue().getId() == userId)
                .forEach(entry -> {
                    if (tickets.containsKey(entry.getKey())) {
                        ticketList.add(tickets.get(entry.getKey()));
                    }
                });

        return ticketList;
    }

    @Override
    public void saveBookedTicket(User user, Ticket ticket) {
        if (user != null && user.getId() != null && ticket != null && ticket.getId() != null) {
            bookedTickets.put(ticket.getId(), user);
        }
    }

    @Override
    public void deleteBookedTicketByUserId(long userId) {
        bookedTickets.values().removeIf(u -> u.getId() == userId);
    }

    public void cleanUp() {
        if (tickets != null) {
            tickets.clear();
        }
        if (bookedTickets != null) {
            bookedTickets.clear();
        }
        id = 0;
    }
}

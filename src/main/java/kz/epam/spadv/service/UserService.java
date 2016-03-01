package kz.epam.spadv.service;

import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;

import java.time.LocalDate;
import java.util.Collection;

public interface UserService {
    User create(String name, String email, LocalDate birthDay);
    User register(User user);
    void remove(User user);
    User getById(long id);
    User getUserByEmail(String email);
    User getUserByName(String name);
    Collection<Ticket> getBookedTickets();
    Collection<Ticket> getBookedTicketsByUserId(long userId);
}

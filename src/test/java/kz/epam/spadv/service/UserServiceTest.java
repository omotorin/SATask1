package kz.epam.spadv.service;

import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.repository.TicketRepository;
import kz.epam.spadv.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-map.xml")
public class UserServiceTest {

    @Test
    public void shouldRegisterUser() {
        User user = new User(1L, "Oleg", "email");
        UserRepository mockRepository = mock(UserRepository.class);

        UserService userService = new UserServiceImpl(mockRepository);
        userService.register(user);

        verify(mockRepository).save(user);
    }

    @Test
    public void shouldRemoveUser() {
        User user = new User(1L, "Oleg", "email");
        UserRepository userRepository = mock(UserRepository.class);

        UserService userService = new UserServiceImpl(userRepository);
        userService.remove(user);

        verify(userRepository).delete(user.getId());
    }

    @Test
    public void shouldGetUserById() {
        User user = new User(1L, "Oleg", "email");
        UserRepository mockRepository = mock(UserRepository.class);
        when(mockRepository.findById(user.getId())).thenReturn(user);

        UserService userService = new UserServiceImpl(mockRepository);
        User u = userService.getById(user.getId());
        assertThat(u, is(user));

        verify(mockRepository).findById(user.getId());
    }

    @Test
    public void shouldGetUserByEmail() {
        User user = new User(1L, "Oleg", "email");
        UserRepository mockRepository = mock(UserRepository.class);
        when(mockRepository.findByEmail(user.getEmail())).thenReturn(user);

        UserService userService = new UserServiceImpl(mockRepository);
        User u = userService.getUserByEmail(user.getEmail());
        assertThat(u, is(user));

        verify(mockRepository).findByEmail(user.getEmail());
    }

    @Test
    public void shouldGetUserByName() {
        User user1 = new User(1L, "Oleg", "email1");
        User user2 = new User(2L, "Oleg1", "email2");
        UserRepository mockRepository = mock(UserRepository.class);
        when(mockRepository.findByName(user1.getName())).thenReturn(user1);

        UserService userService = new UserServiceImpl(mockRepository);
        User user = userService.getUserByName(user1.getName());
        assertThat(user, is(user1));

        verify(mockRepository).findByName(user1.getName());
    }

    @Test
    public void shouldGetBookedTickets(){
        User user1 = new User(1L, "Oleg", "email1");
        User user2 = new User(2L, "Oleg", "email2");

        TicketRepository ticketRepository = mock(TicketRepository.class);

        ticketRepository.saveBookedTicket(user1, new Ticket());
        ticketRepository.saveBookedTicket(user1, new Ticket());
        ticketRepository.saveBookedTicket(user2, new Ticket());
        when(ticketRepository.getBookedTickets()).thenReturn(Arrays.asList(new Ticket(), new Ticket(), new Ticket()));

        UserServiceImpl userService = new UserServiceImpl();
        userService.setTicketRepository(ticketRepository);
        Collection<Ticket> tickets = userService.getBookedTickets();
        assertTrue(tickets.size() == 3);

        verify(ticketRepository).getBookedTickets();
    }
}

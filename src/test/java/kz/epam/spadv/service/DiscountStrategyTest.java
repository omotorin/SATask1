package kz.epam.spadv.service;

import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.repository.MapTicketRepository;
import kz.epam.spadv.repository.TicketRepository;
import kz.epam.spadv.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by Oleg_Motorin on 23.10.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-map.xml")
public class DiscountStrategyTest {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private DiscountStrategy tenTicketStrategy;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldCalculateIfBirthday() throws Exception {
        User user = new User("Oleg", "email", LocalDate.now());
        DiscountStrategy strategy = new BirthDayDiscountStrategy();
        double discount = strategy.calculate(user);
        assertTrue(discount!=0);
    }

    @Test
    public void shouldNotCalculateIfNotBirthday() throws Exception {
        User user = new User("Oleg", "email", LocalDate.parse("1978-08-12"));
        DiscountStrategy strategy = new BirthDayDiscountStrategy();
        double discount = strategy.calculate(user);
        assertTrue(discount==0);
    }

    @Test
    public void shouldCalculateIfTenTicket() throws Exception {
        User user = new User(1, "Oleg", "email");
        userRepository.save(user);
        IntStream.rangeClosed(1, 10).forEach(i->{
            Ticket t=new Ticket();
            t.setId(i);
            ticketRepository.save(t);
            ticketRepository.saveBookedTicket(user, t);
        });
        double discount = tenTicketStrategy.calculate(user);
        assertTrue(discount!=0);
    }

    @Test
    public void shouldNotCalculateIfNotTenTicket() throws Exception {
        User user = new User("Oleg", "email");
        userRepository.save(user);
        List<Ticket> tickets = new ArrayList<>();
        IntStream.rangeClosed(1,11).forEach(i->ticketRepository.saveBookedTicket(user, new Ticket()));
        double discount = tenTicketStrategy.calculate(user);
        assertTrue(discount==0);
    }

    @After
    public void tearDown() throws Exception {
        ((MapTicketRepository)ticketRepository).cleanUp();
    }
}

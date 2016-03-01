package kz.epam.spadv.service;

import kz.epam.spadv.domain.Event;
import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.repository.TicketRepository;
import kz.epam.spadv.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Oleg_Motorin on 05.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-map.xml")
public class DiscountServiceTest {

    @Autowired
    private DiscountService discountService;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldGetMaxDiscount() throws Exception {
        Collection<DiscountStrategy> strategies = discountService.getAllStrategies();
        float maxCoef = (float) strategies.stream().mapToDouble(DiscountStrategy::getDiscountCoef).max().getAsDouble();
        User user = new User(1, "users", "email", LocalDate.now());
        userRepository.save(user);
        Event event = new Event(1, "event", LocalDateTime.now(), 100F, Rating.HIGH);
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Ticket t = new Ticket();
            t.setId(i);
            ticketRepository.save(t);
            ticketRepository.saveBookedTicket(user, t);
        });
        float discount = discountService.getDiscount(user, event, LocalDateTime.now());
        assertEquals(maxCoef, discount);
    }
}

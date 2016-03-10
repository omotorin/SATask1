package kz.epam.spadv;

import kz.epam.spadv.repository.exception.AccountNotFoundException;
import kz.epam.spadv.service.BookingService;
import kz.epam.spadv.service.Rating;
import kz.epam.spadv.service.exception.*;
import kz.epam.spadv.domain.Auditorium;
import kz.epam.spadv.domain.Event;
import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import kz.epam.spadv.repository.AuditoriumRepository;
import kz.epam.spadv.service.EventService;
import kz.epam.spadv.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

public class App {

    static Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws ParseException, UserNotRegisteredException,
            TicketAlreadyBookedException, EventNotAssignedException, TicketWithoutEventException, NotEnoughMoneyForWithdrawal, AccountNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-with-jdbc.xml");

        UserService userService = (UserService) context.getBean("userService");
        EventService eventService = (EventService) context.getBean("eventService");
        AuditoriumRepository auditoriumRepository = (AuditoriumRepository) context.getBean("auditoriumRepository");
        BookingService bookingService = (BookingService) context.getBean("bookingService");

        User user1 = userService.create("Oleg1", "email1", LocalDate.parse("1978-08-12"));
        User user2 = userService.create("Oleg2", "email2", LocalDate.parse("1978-11-03"));

        LocalDateTime eventDate = LocalDateTime.parse("2015-11-01T14:00");
        Auditorium auditorium = auditoriumRepository.getById(1);

        Event event1 = eventService.create("event1", 100F, Rating.HIGH);
        Event event2 = eventService.create("event2", 200F, Rating.MID);

        try {
            eventService.assignAuditorium(event1, auditorium, eventDate);
            eventService.assignAuditorium(event2, auditorium, eventDate);
        } catch (AuditoriumAlreadyAssignedException e) {
            log.error(e.getMessage());
        }

        userService.register(user1);
        userService.register(user2);

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Ticket ticket = new Ticket();
            ticket.setEvent(event1);
            ticket.setPrice(event1.getTicketPrice());
            ticket.setSeat(auditoriumRepository.getSeatByAuditoriumIdAndNumber(auditorium.getId(), i));
            try {
                bookingService.bookTicket(user1, ticket);
            } catch (UserNotRegisteredException | TicketAlreadyBookedException | TicketWithoutEventException e) {
                throw new RuntimeException(e);
            } catch (AccountNotFoundException e) {
                e.printStackTrace();
            } catch (NotEnoughMoneyForWithdrawal notEnoughMoneyForWithdrawal) {
                notEnoughMoneyForWithdrawal.printStackTrace();
            }
        });

        Ticket ticket = new Ticket();
        ticket.setEvent(event1);
        ticket.setPrice(event1.getTicketPrice());
        ticket.setSeat(auditoriumRepository.getSeatByAuditoriumIdAndNumber(auditorium.getId(), 1));
        try {
            bookingService.bookTicket(user2, ticket);
        } catch (TicketAlreadyBookedException e) {
            log.error(e.getMessage());
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        } catch (NotEnoughMoneyForWithdrawal notEnoughMoneyForWithdrawal) {
            notEnoughMoneyForWithdrawal.printStackTrace();
        }
        ticket.setSeat(auditoriumRepository.getSeatByAuditoriumIdAndNumber(auditorium.getId(), 30));
        bookingService.bookTicket(user2, ticket);

        float price = bookingService.getTicketPrice(event1, eventDate, Arrays.asList(1), user1);
        log.info("Ticket price for users <" + user1.getName() + "> is: " + price);

        price = bookingService.getTicketPrice(event1, eventDate, Arrays.asList(15, 30), user2);
        log.info("Ticket price for users <" + user2.getName() + "> is: " + price);
    }
}

package kz.epam.spadv.web;

import kz.epam.spadv.domain.*;
import kz.epam.spadv.repository.exception.AccountNotFoundException;
import kz.epam.spadv.service.BookingService;
import kz.epam.spadv.service.EventService;
import kz.epam.spadv.service.Roles;
import kz.epam.spadv.service.UserService;
import kz.epam.spadv.service.exception.NotEnoughMoneyForWithdrawal;
import kz.epam.spadv.service.exception.TicketAlreadyBookedException;
import kz.epam.spadv.service.exception.TicketWithoutEventException;
import kz.epam.spadv.service.exception.UserNotRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Oleg_Motorin on 2/17/2016.
 */
@Controller @RequestMapping("/users") public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('BOOKING_MANAGER')")
    @RequestMapping(method = RequestMethod.GET, params = "new")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("editUser");
        mav.addObject(new User());
        mav.addObject("roles", Roles.values());
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String register(@ModelAttribute("userForm") UserForm form) {
        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setBirthday(LocalDate.parse(form.getBirthday()));
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRoles(Arrays.asList(form.getRole()));
        userService.register(user);
        return "redirect:/users/id/" + user.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id/{userId}/delete")
    public @ResponseBody String delete(
            @PathVariable long userId) {
        User user = userService.getById(userId);
        if (user != null) {
            userService.remove(user);
            return "<h1>User successfully deleted!</h1>";
        } else {
            return "<h1>Sorry, user not found!<h1>";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id/{userId}")
    public ModelAndView getById(@PathVariable long userId) {
        ModelAndView mav = new ModelAndView("displayUser");
        mav.addObject("user", userService.getById(userId));
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/email/{userEmail}")
    public ModelAndView getByEmail(@PathVariable String userEmail) {
        ModelAndView mav = new ModelAndView("displayUser");
        mav.addObject("user", userService.getUserByEmail(userEmail));
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/name/{userName}")
    public ModelAndView getByName(@PathVariable String userName) throws UserNotRegisteredException {
        ModelAndView mav = new ModelAndView("displayUser");
        User user = userService.getUserByName(userName);
        if(user==null){
            throw new UserNotRegisteredException();
        }
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id/{userId}/tickets")
    public ModelAndView getBookedTicketsByUser(@PathVariable long userId) {
        ModelAndView mav = new ModelAndView("displayTickets");
        mav.addObject("userId", userId);
        Collection<Ticket> tickets = userService.getBookedTicketsByUserId(userId);
        if (!tickets.isEmpty()) {
            mav.addObject("tickets", tickets);
        }
        return mav;
    }

    @RequestMapping(value = "id/{userId}/book", method = RequestMethod.GET)
    public ModelAndView viewBook(@PathVariable long userId){
        ModelAndView mav = new ModelAndView("bookTicket");
        mav.addObject("userId", userId);
        mav.addObject("events", eventService.getAll());
        return mav;
    }

    @RequestMapping(value = "id/{userId}/book", method = RequestMethod.POST)
    public ModelAndView book(@PathVariable long userId, @RequestParam Long eventId,
                             @RequestParam(required = false) Long seatNumber
    ) throws UserNotRegisteredException, TicketAlreadyBookedException, TicketWithoutEventException, NotEnoughMoneyForWithdrawal, AccountNotFoundException {
        if(eventId!=null && seatNumber!=null) {
            Event event = eventService.getById(eventId);
            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setPrice(event.getTicketPrice());
            Seat s = event.getAuditorium().getSeats().stream()
                    .filter(seat ->  seat.getNumber()==seatNumber)
                    .findFirst().orElse(null);
            ticket.setSeat(s);
            User user = userService.getById(userId);
            bookingService.bookTicket(user, ticket);
            return new ModelAndView("displayUser").addObject("user", user);
        }
        ModelAndView mav = new ModelAndView("bookTicket");
        mav.addObject("user", userId);
        mav.addObject("events", eventService.getAll());
        mav.addObject("eventId", eventId);
        if(eventId!=null){
            Event event = eventService.getById(eventId);
            Auditorium auditorium = event.getAuditorium();
            mav.addObject("auditorium", auditorium);
        }
        return mav;
    }
}

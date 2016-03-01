package kz.epam.spadv.web;

import kz.epam.spadv.domain.Event;
import kz.epam.spadv.repository.AuditoriumRepository;
import kz.epam.spadv.service.EventService;
import kz.epam.spadv.service.Rating;
import kz.epam.spadv.service.exception.AuditoriumAlreadyAssignedException;
import kz.epam.spadv.service.exception.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Oleg_Motorin on 2/26/2016.
 */
@Controller @RequestMapping("/events")
public class EventController {

    @Autowired private EventService eventService;
    @Autowired private AuditoriumRepository auditoriumRepository;

    @RequestMapping(params = "new")
    public String addEvent(){
        return "editEvent";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute EventForm eventForm) {
        Event event = eventService.create(eventForm.getName(), LocalDateTime.parse(eventForm.getDateTime()),
                eventForm.getTicketPrice(), Rating.valueOf(eventForm.getRating()));
        ModelAndView mav = new ModelAndView("displayEvent");
        mav.addObject("event", event);
        mav.addObject("auditoriums", auditoriumRepository.getAuditoriums());
        return mav;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = "name")
    public ModelAndView doFindEvent(@RequestParam String name) throws EventNotFoundException {
        Event event = eventService.getByName(name);
        if (event == null) {
            throw new EventNotFoundException();
        }
        ModelAndView mav = new ModelAndView("displayEvent");
        mav.addObject("event", event);
        mav.addObject("auditoriums", auditoriumRepository.getAuditoriums());
        return mav;
    }

    @RequestMapping(value = "/id/{eventId}", method = RequestMethod.GET)
    public ModelAndView doFindEvent(@PathVariable long eventId) throws EventNotFoundException {
        Event event = eventService.getById(eventId);
        if (event == null) {
            throw new EventNotFoundException();
        }
        ModelAndView mav = new ModelAndView("displayEvent");
        mav.addObject("event", event);
        mav.addObject("auditoriums", auditoriumRepository.getAuditoriums());
        return mav;
    }

    @RequestMapping(value = "/id/{eventId}/assign", method = RequestMethod.POST)
    public ModelAndView assign(@PathVariable long eventId, @RequestParam int auditoriumId)
            throws EventNotFoundException, AuditoriumAlreadyAssignedException {
        Event event = eventService.getById(eventId);
        if(event==null){
            throw new EventNotFoundException();
        }
        event = eventService.assignAuditorium(event, auditoriumRepository.getById(auditoriumId), event.getDateTime());
        ModelAndView mav = new ModelAndView("displayEvent");
        mav.addObject("event", event);
        mav.addObject("auditoriums", auditoriumRepository.getAuditoriums());
        return mav;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllEvents() {
        ModelAndView mav = new ModelAndView("displayEvents");
        Collection<Event> events = eventService.getAll();
        if (!events.isEmpty()) {
            mav.addObject("events", events);
        }
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/id/{eventId}/delete")
    public ModelAndView delete(
            @PathVariable long eventId) {
        Event event = eventService.getById(eventId);
        ModelAndView mav = new ModelAndView("displayEvent");
        if (event != null) {
            eventService.remove(event);
            mav.addObject("deleted", true);
        }
        return mav;
    }
}

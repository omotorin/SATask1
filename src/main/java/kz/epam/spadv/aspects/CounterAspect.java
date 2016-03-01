package kz.epam.spadv.aspects;

import kz.epam.spadv.repository.CounterRepository;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Oleg_Motorin on 23.10.2015.
 */
@Aspect
@Component
public class CounterAspect {

    private static Logger log=Logger.getLogger(CounterAspect.class);

    @Autowired
    private CounterRepository counterRepository;

    @Pointcut("execution(* kz.epam.spadv.service.EventService.getByName(..))")
    public void pointcutEventByName() {
    }

    @Pointcut("execution(* kz.epam.spadv.service.BookingService.getTicketPrice(..))")
    public void pointcutPriceQueried() {
    }

    @Pointcut("execution(* kz.epam.spadv.service.BookingService.bookTicket(..))")
    public void pointcutTicketBooked() {
    }

    @AfterReturning("pointcutEventByName()")
    public void countEventAccessedByName() {
        Integer count = counterRepository.getByName(Counters.EVENT_ACCESSED_BY_NAME.name()) + 1;
        counterRepository.save(Counters.EVENT_ACCESSED_BY_NAME.name(), count);
        log.info(Counters.EVENT_ACCESSED_BY_NAME + ": " + count + " times");
    }

    @AfterReturning("pointcutPriceQueried()")
    public void countEventPriceQueried() {
        Integer count = counterRepository.getByName(Counters.PRICE_QUERIED.name()) + 1;
        counterRepository.save(Counters.PRICE_QUERIED.name(), count);
        log.info(Counters.PRICE_QUERIED + ": " + count + " times");
    }

    @AfterReturning("pointcutTicketBooked()")
    public void countTicketBooked() {
        Integer count = counterRepository.getByName(Counters.TICKET_BOOKED.name()) + 1;
        counterRepository.save(Counters.TICKET_BOOKED.name(), count);
        log.info(Counters.TICKET_BOOKED + ": " + count + " times");
    }
}

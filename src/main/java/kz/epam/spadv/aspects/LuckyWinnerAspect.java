package kz.epam.spadv.aspects;

import kz.epam.spadv.domain.Win;
import kz.epam.spadv.repository.WinsRepository;
import kz.epam.spadv.domain.Ticket;
import kz.epam.spadv.domain.User;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * Created by Oleg_Motorin on 23.10.2015.
 */
@Aspect
@Component
public class LuckyWinnerAspect {

    private static final float PRICE = 0F;
    private static final int LUCKY = 5;
    private static Logger log = Logger.getLogger(LuckyWinnerAspect.class);
    private Random random = new Random();

    @Autowired
    private WinsRepository winsRepository;

    @Pointcut("execution(* kz.epam.spadv.service.BookingService.bookTicket(..))")
    public void pointcutLuckyWinner() {
    }

    @Around("pointcutLuckyWinner()")
    public void luckyWinner(ProceedingJoinPoint jp) throws Throwable {
        User user = (User) jp.getArgs()[0];
        Ticket ticket = (Ticket) jp.getArgs()[1];
        if (checkLucky() && ticket != null) {
            Win win = new Win();
            win.setDate(new Date());
            win.setUser(user);
            winsRepository.save(win);
            ticket.setPrice(PRICE);
            jp.proceed(new Object[]{user, ticket});
            log.info("User " + user.getName() + " is lucky winner");
        } else {
            jp.proceed();
        }
    }

    private boolean checkLucky() {
        int rnd = random.nextInt() % LUCKY;
        return rnd == 0;
    }

}

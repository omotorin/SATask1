package kz.epam.spadv.aspects;

import kz.epam.spadv.repository.CounterRepository;
import kz.epam.spadv.domain.User;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Oleg_Motorin on 23.10.2015.
 */
@Aspect
@Component
public class DiscountAspect {

    private static Logger log = Logger.getLogger(DiscountAspect.class);

    @Autowired
    private CounterRepository counterRepository;

    @Pointcut("execution(* kz.epam.spadv.service.DiscountService.getDiscount(..))")
    public void pointcutDiscount() {
    }

    @Around("pointcutDiscount()")
    public float count(ProceedingJoinPoint jp) throws Throwable {
        float discount = (float) jp.proceed();
        User user = (User) jp.getArgs()[0];
        if (discount != 0 && user != null) {
            String counterName = Counters.DISCOUNT_USER_ID.name() + "_" + user.getId();
            int count = counterRepository.getByName(counterName) + 1;
            counterRepository.save(counterName, count);
            log.info(counterName + ": " + count);

            count = counterRepository.getByName(Counters.DISCOUNT_TOTAL.name()) + 1;
            counterRepository.save(Counters.DISCOUNT_TOTAL.name(), count);
            log.info(Counters.DISCOUNT_TOTAL+": "+count);
        }
        return discount;
    }
}

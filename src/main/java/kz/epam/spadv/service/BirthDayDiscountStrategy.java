package kz.epam.spadv.service;

import kz.epam.spadv.domain.User;
import org.apache.log4j.Logger;

import java.time.LocalDate;

/**
 * Created by ���� on 22.10.2015.
 */
public class BirthDayDiscountStrategy implements DiscountStrategy {

    private static final Logger log = Logger.getLogger(BirthDayDiscountStrategy.class);

    private static final float DISCOUNT = 0.05F;

    @Override
    public float calculate(User user) {
        float discount = 0;
        if (user != null && user.getBirthday() != null) {
            if (user.getBirthday().getDayOfMonth() == LocalDate.now().getDayOfMonth()
                    && user.getBirthday().getMonthValue() == LocalDate.now().getMonthValue()) {
                discount = DISCOUNT;
                log.info("Today is birthday of " + user.getName());
            }
        }
        return discount;
    }

    @Override
    public float getDiscountCoef() {
        return DISCOUNT;
    }
}

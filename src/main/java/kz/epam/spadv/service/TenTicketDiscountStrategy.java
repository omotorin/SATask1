package kz.epam.spadv.service;

import kz.epam.spadv.repository.TicketRepository;
import kz.epam.spadv.domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by ���� on 22.10.2015.
 */
@Component
public class TenTicketDiscountStrategy implements DiscountStrategy {

    private static final Logger log = Logger.getLogger(TenTicketDiscountStrategy.class);

    private static final int EACH_N_TICKET = 10;
    private static final float DISCOUNT = 0.5F;
    private static final float DEFAULT_DISCOUNT = 0;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public float calculate(User user) {
        final float[] discount = {DEFAULT_DISCOUNT};
        Optional.ofNullable(user)
                .ifPresent(u -> {
                    int count = ticketRepository.getBookedTicketsByUserId(u.getId()).size();
                    if (count > 0) {
                        discount[0] = (count % EACH_N_TICKET) == 0 ? DISCOUNT : DEFAULT_DISCOUNT;
                    }
                });

        if (discount[0] == DISCOUNT) {
            log.info("User " + user.getName() + " purchased tenth ticket!");
        }

        return discount[0];
    }

    @Override
    public float getDiscountCoef() {
        return DISCOUNT;
    }
}

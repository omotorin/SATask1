package kz.epam.spadv.service;

import kz.epam.spadv.domain.Event;
import kz.epam.spadv.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by ���� on 22.10.2015.
 */
public class DiscountServiceImpl implements DiscountService {

    private Collection<DiscountStrategy> strategies;

    @Autowired
    public DiscountServiceImpl(Collection<DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public float getDiscount(User user, Event event, LocalDateTime dateTime) {
        final double[] discount = {0};
        strategies.stream()
                .mapToDouble(strategy -> strategy.calculate(user))
                .max().ifPresent(d -> discount[0] = d);
        return (float) discount[0];
    }

    @Override
    public Collection<DiscountStrategy> getAllStrategies() {
        return strategies;
    }
}

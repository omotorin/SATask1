package kz.epam.spadv.service;

import kz.epam.spadv.domain.Event;
import kz.epam.spadv.domain.User;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by ���� on 22.10.2015.
 */
public interface DiscountService {
    float getDiscount(User user, Event event, LocalDateTime dateTime);
    Collection<DiscountStrategy> getAllStrategies();
}

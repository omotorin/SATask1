package kz.epam.spadv.service;

import kz.epam.spadv.domain.User;

/**
 * Created by ���� on 22.10.2015.
 */
public interface DiscountStrategy {
    float calculate(User user);
    float getDiscountCoef();
}

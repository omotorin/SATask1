package kz.epam.spadv.service;

/**
 * Created by Oleg_Motorin on 3/9/2016.
 */
public enum Roles {
    REGISTERED_USER("Registered user"),
    BOOKING_MANAGER("Booking manager");

    private String role;

    Roles(String role) {
        this.role = role;
    }
}

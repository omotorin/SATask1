package kz.epam.spadv.service.exception;

/**
 * Created by ���� on 22.10.2015.
 */
public class UserNotRegisteredException extends Exception {
    public UserNotRegisteredException() {
        super("Sorry, user not found!");
    }
}

package kz.epam.spadv.service.exception;

/**
 * Created by Oleg_Motorin on 03.11.2015.
 */
public class EventNotFoundException extends Exception{
    public EventNotFoundException() {
        super("Sorry, event not found!");
    }
}

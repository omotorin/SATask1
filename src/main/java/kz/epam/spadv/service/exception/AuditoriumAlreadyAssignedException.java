package kz.epam.spadv.service.exception;

import kz.epam.spadv.domain.Auditorium;
import kz.epam.spadv.domain.Event;

/**
 * Created by Oleg_Motorin on 03.11.2015.
 */
public class AuditoriumAlreadyAssignedException extends Exception {
    private String message;

    public AuditoriumAlreadyAssignedException(Event event, Auditorium auditorium) {
        message = String.format("Event <%s> is not assigned to auditorium <%s> because it's already assigned to another event",
                event.getName(),
                auditorium.getName());
    }

    @Override
    public String getMessage() {
        return message;
    }
}

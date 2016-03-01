package kz.epam.spadv.service.exception;

/**
 * Created by Oleg_Motorin on 28.10.2015.
 */
public class TicketAlreadyBookedException extends Exception {
    private String message;

    public TicketAlreadyBookedException(int seatNumber) {
        message = String.format("Ticket for seat number %d  already booked!", seatNumber);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

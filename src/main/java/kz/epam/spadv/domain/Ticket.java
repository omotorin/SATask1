package kz.epam.spadv.domain;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
public class Ticket {
    private Long id;
    private float price;
    private Seat seat;
    private Event event;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (Float.compare(ticket.price, price) != 0) return false;
        if (id != null ? !id.equals(ticket.id) : ticket.id != null) return false;
        if (seat != null ? !seat.equals(ticket.seat) : ticket.seat != null) return false;
        return !(event != null ? !event.equals(ticket.event) : ticket.event != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }
}

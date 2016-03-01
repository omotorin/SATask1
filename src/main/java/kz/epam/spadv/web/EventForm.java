package kz.epam.spadv.web;

/**
 * Created by Oleg_Motorin on 2/24/2016.
 */
public class EventForm {
    private String name;
    private String dateTime;
    private float ticketPrice;
    private String rating;

    public EventForm(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

package kz.epam.spadv.domain;

import kz.epam.spadv.service.Rating;
import kz.epam.spadv.xml.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
@XmlRootElement(name = "event")
@XmlType(propOrder = {"id","name","dateTime","ticketPrice","rating","auditorium"})
public class Event {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private float ticketPrice;
    private Rating rating;
    private Auditorium auditorium;

    public Event(){}

    public Event(long id, String name, LocalDateTime dateTime, float ticketPrice, Rating rating) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
        this.rating = rating;
    }

    public Event(String name, LocalDateTime dateTime, float ticketPrice, Rating rating) {
        this.name = name;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
        this.rating = rating;
    }

    @XmlElement(required = true)
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @XmlElement(required = true)
    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @XmlElement(required = true)
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @XmlElement(required = true)
    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (Float.compare(event.ticketPrice, ticketPrice) != 0) return false;
        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (dateTime != null ? !dateTime.equals(event.dateTime) : event.dateTime != null) return false;
        if (rating != event.rating) return false;
        return !(auditorium != null ? !auditorium.equals(event.auditorium) : event.auditorium != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (ticketPrice != +0.0f ? Float.floatToIntBits(ticketPrice) : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (auditorium != null ? auditorium.hashCode() : 0);
        return result;
    }
}

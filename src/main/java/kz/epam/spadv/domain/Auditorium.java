package kz.epam.spadv.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
public class Auditorium {

    private static final String AUDITORIUM_NAME = "auditorium.name";
    private static final String AUDITORIUM_NUMBER_OF_SEATS = "auditorium.number_of_seats";
    private static final String AUDITORIUM_VIP_SEATS = "auditorium.vip_seats";

    private int id;
    private String name;
    private int numberOfSeats;
    private Collection<Seat> seats;

    public Auditorium(int id){
        this.id = id;
    }

    public Auditorium(int id, String name, int numberOfSeats, String vipSeats){
        this.seats = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        Collection<Integer> vipList = Stream.of(vipSeats.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        IntStream.rangeClosed(1, numberOfSeats).forEach(n ->
                        seats.add(new Seat(n, vipList.contains(n)))
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Collection<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Collection<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auditorium that = (Auditorium) o;

        if (id != that.id) return false;
        if (numberOfSeats != that.numberOfSeats) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(seats != null ? !seats.equals(that.seats) : that.seats != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + numberOfSeats;
        result = 31 * result + (seats != null ? seats.hashCode() : 0);
        return result;
    }
}

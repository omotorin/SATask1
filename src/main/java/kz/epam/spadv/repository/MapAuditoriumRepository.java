package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Auditorium;
import kz.epam.spadv.domain.Seat;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * Created by ���� on 22.10.2015.
 */
public class MapAuditoriumRepository implements AuditoriumRepository {

    @Value("#{auditoriums}")
    private Map<Integer, Auditorium> auditoriums;

    public MapAuditoriumRepository(){
    }

    @Override
    public Auditorium getById(int id) {
        return auditoriums.get(id);
    }

    @Override
    public List<Auditorium> getAuditoriums() {
        return new ArrayList<>(auditoriums.values());
    }

    @Override
    public long getSeatsNumber(int auditoriumId) {
        return auditoriums.get(auditoriumId).getNumberOfSeats();
    }

    @Override
    public Collection<Seat> getSeats(int auditoriumId) {
        return auditoriums.get(auditoriumId).getSeats();
    }

    @Override
    public Seat getSeatByAuditoriumIdAndNumber(int auditoriumId, int number) {
        Optional<Seat> seat = getSeats(auditoriumId).stream()
                .filter(s -> s.getNumber() == number).findFirst();
        if(seat.isPresent()){
            return seat.get();
        }
        return null;
    }
}

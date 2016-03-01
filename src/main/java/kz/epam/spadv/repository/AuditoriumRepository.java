package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Auditorium;
import kz.epam.spadv.domain.Seat;

import java.util.Collection;
import java.util.List;

/**
 * Created by ���� on 22.10.2015.
 */
public interface AuditoriumRepository {
    Auditorium getById(int id);
    List<Auditorium> getAuditoriums();
    long getSeatsNumber(int auditoriumId);
    Collection<Seat> getSeats(int auditoriumId);
    Seat getSeatByAuditoriumIdAndNumber(int auditoriumId, int number);
}

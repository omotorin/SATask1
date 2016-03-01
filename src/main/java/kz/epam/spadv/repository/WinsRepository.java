package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Win;

import java.util.List;

/**
 * Created by Oleg_Motorin on 27.10.2015.
 */
public interface WinsRepository {
    Win save(Win win);
    List<Win> getAll();
    List<Win> getByUserId(long userId);
    void delete(long userId);
}

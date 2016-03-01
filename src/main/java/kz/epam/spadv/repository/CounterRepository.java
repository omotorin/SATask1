package kz.epam.spadv.repository;

/**
 * Created by Oleg_Motorin on 23.10.2015.
 */
public interface CounterRepository {
    void save(String counterName, int count);

    int getByName(String counterName);

    void increment(String counterName);
}

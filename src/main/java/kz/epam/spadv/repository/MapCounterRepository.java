package kz.epam.spadv.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg_Motorin on 23.10.2015.
 */
public class MapCounterRepository implements CounterRepository {

    private Map<String, Integer> counters;

    public MapCounterRepository() {
        this.counters = new HashMap<>();
    }

    @Override
    public void save(String counterName, int count) {
        counters.put(counterName, count);
    }

    @Override
    public int getByName(String counterName) {
        counters.putIfAbsent(counterName, 0);
        return counters.get(counterName);
    }

    @Override
    public void increment(String counterName) {
        counters.computeIfPresent(counterName, (key, counter) -> counter + 1);
    }

    public Map<String, Integer> getCounters() {
        return counters;
    }
}

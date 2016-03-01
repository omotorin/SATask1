package kz.epam.spadv.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Oleg_Motorin on 28.10.2015.
 */
public class JdbcCounterRepository implements CounterRepository {

    private static final String UPDATE_COUNTER = "UPDATE counters SET counter=? WHERE name=?";
    private static final String INSERT_COUNTER = "INSERT INTO counters(name, counter) VALUES (?,?)";
    private static final String SELECT_BY_NAME = "SELECT counter FROM counters WHERE name=?";
    private static final String INCREMENT_COUNTER = "UPDATE counters SET counter = counter+1 WHERE name=?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(String counterName, int count) {
        if (counterName != null && !counterName.isEmpty()) {
            int updatedRows = jdbcTemplate.update(UPDATE_COUNTER, count, counterName);
            if (updatedRows == 0) {
                jdbcTemplate.update(INSERT_COUNTER, counterName, count);
            }
        }
    }

    @Override
    public int getByName(String counterName) {
        Integer count = null;
        try{
            count = jdbcTemplate.queryForObject(SELECT_BY_NAME, Integer.class, counterName);
        } catch (EmptyResultDataAccessException ex){
            count = 0;
        }
        return count;
    }

    @Override
    public void increment(String counterName) {
        jdbcTemplate.update(INCREMENT_COUNTER, counterName);
    }
}

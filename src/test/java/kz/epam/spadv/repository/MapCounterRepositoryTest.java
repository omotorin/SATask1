package kz.epam.spadv.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oleg_Motorin on 09.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-map.xml")
public class MapCounterRepositoryTest {

    @Autowired
    private CounterRepository counterRepository;

    @Test
    public void shouldSave() throws Exception {
        counterRepository.save("counter1", 2);
        counterRepository.save("counter2", 0);
        Map<String, Integer> counters = ((MapCounterRepository) counterRepository).getCounters();
        assertEquals(2, counters.size());
        assertEquals(true, counters.containsKey("counter1"));
        assertEquals(true, counters.containsKey("counter2"));
        assertEquals(2, counters.get("counter1").longValue());
        assertEquals(0, counters.get("counter2").longValue());
    }

    @Test
    public void shouldGetByName() throws Exception {
        counterRepository.save("counter1", 1);
        int counter = counterRepository.getByName("counter1");
        assertEquals(1, counter);
    }

    @Test
    public void shouldReturnZeroIfNotFoundedByName() throws Exception {
        counterRepository.save("counter1", 1);
        int counter = counterRepository.getByName("counter3");
        assertEquals(0, counter);
    }

    @Test
    public void shouldIncrement() throws Exception {
        counterRepository.save("counter1", 1);
        counterRepository.increment("counter1");
        Map<String, Integer> counters = ((MapCounterRepository) counterRepository).getCounters();
        assertEquals(2, counters.get("counter1").longValue());
    }

    @Test
    public void shouldNotIncrementIfCounterNotExist() throws Exception {
        counterRepository.save("counter1", 1);
        counterRepository.save("counter2", 1);
        counterRepository.increment("counter3");
        Map<String, Integer> counters = ((MapCounterRepository) counterRepository).getCounters();
        assertEquals(2, counters.size());
        assertEquals(true, counters.containsKey("counter1"));
        assertEquals(true, counters.containsKey("counter2"));
        assertEquals(1, counters.get("counter1").longValue());
        assertEquals(1, counters.get("counter2").longValue());
    }
}

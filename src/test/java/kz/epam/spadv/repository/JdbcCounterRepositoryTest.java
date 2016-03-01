package kz.epam.spadv.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Oleg_Motorin on 06.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-jdbc.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, ForeignKeyDisabling.class})
public class JdbcCounterRepositoryTest {

    @Autowired
    private CounterRepository counterRepository;

    @Test
    @DatabaseSetup("classpath:/data/initCounters.xml")
    @ExpectedDatabase("classpath:/data/counterSaved.xml")
    public void shouldSave() throws Exception {
        counterRepository.save("counter1", 2);
        counterRepository.save("counter2", 0);
    }

    @Test
    @DatabaseSetup("classpath:/data/initCounters.xml")
    public void shouldGetByName() throws Exception {
        int counter = counterRepository.getByName("counter1");
        assertEquals(1, counter);
    }

    @Test
    @DatabaseSetup("classpath:/data/initCounters.xml")
    public void shouldReturnZeroIfNotFoundedByName() throws Exception {
        int counter = counterRepository.getByName("counter3");
        assertEquals(0, counter);
    }

    @Test
    @DatabaseSetup("classpath:/data/initCounters.xml")
    @ExpectedDatabase("classpath:/data/counterIncremented.xml")
    public void shouldIncrement() throws Exception {
        counterRepository.increment("counter1");
    }

    @Test
    @DatabaseSetup("classpath:/data/initCounters.xml")
    @ExpectedDatabase("classpath:/data/initCounters.xml")
    public void shouldNotIncrementIfCounterNotExist() throws Exception {
        counterRepository.increment("counter2");
    }
}

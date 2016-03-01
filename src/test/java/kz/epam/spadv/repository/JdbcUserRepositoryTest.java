package kz.epam.spadv.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import kz.epam.spadv.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Oleg_Motorin on 05.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-jdbc.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, ForeignKeyDisabling.class})
public class JdbcUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("classpath:/data/initUsers.xml")
    @ExpectedDatabase("classpath:/data/userSaved.xml")
    public void shouldSave() throws Exception {
        User user = new User("user4", "email4", LocalDate.parse("1978-08-15"));
        userRepository.save(user);
    }

    @Test
    @DatabaseSetup("classpath:/data/initUsers.xml")
    @ExpectedDatabase("classpath:/data/userDeleted.xml")
    public void shouldDelete() throws Exception {
        userRepository.delete(2);
    }

    @Test
    @DatabaseSetup("classpath:/data/initUsers.xml")
    public void shouldFindById() throws Exception {
        User expectedUser = new User(0, "user1", "email1", LocalDate.parse("1978-08-12"));
        User user = userRepository.findById(0);
        assertEquals(expectedUser, user);
    }

    @Test
    @DatabaseSetup("classpath:/data/initUsers.xml")
    public void shouldReturnNullIfNotFoundedById() throws Exception {
        User user = userRepository.findById(10);
        assertEquals(null, user);
    }

    @Test
    @DatabaseSetup("classpath:/data/initUsers.xml")
    public void shouldFindByEmail() throws Exception {
        User expectedUser = new User(0, "user1", "email1", LocalDate.parse("1978-08-12"));
        User user = userRepository.findByEmail("email1");
        assertEquals(expectedUser, user);
    }

    @Test
    @DatabaseSetup("classpath:/data/initUsers.xml")
    public void shouldReturnNullIfNotFoundedByEmail() throws Exception {
        User user = userRepository.findByEmail("email10");
        assertEquals(null, user);
    }

    @Test
    @DatabaseSetup("classpath:/data/initUsers.xml")
    public void shouldFindByName() throws Exception {
        User expectedUser = new User(0, "user1", "email1", LocalDate.parse("1978-08-12"));
        User user = userRepository.findByName("user1");
        assertEquals(expectedUser, user);
    }

    @Test
    @DatabaseSetup("classpath:/data/initUsers.xml")
    public void shouldReturnNullIfNotFoundedByName() throws Exception {
        User user = userRepository.findByName("user10");
        assertEquals(null, user);
    }

    @Test
    @DatabaseSetup("classpath:/data/initUsers.xml")
    public void shouldGetAll() throws Exception {
        Collection<User> users = userRepository.getAll();
        assertEquals(3, users.size());
    }

}

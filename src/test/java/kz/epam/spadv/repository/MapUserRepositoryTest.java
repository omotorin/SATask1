package kz.epam.spadv.repository;

import kz.epam.spadv.domain.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-map.xml")
public class MapUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSave() throws Exception {
        User user = new User(1L, "Oleg", "email");
        userRepository.save(user);
        assertThat(userRepository.findById(1L), is(user));
    }

    @Test
    public void shouldDelete() throws Exception {
        User user = new User(1L, "Oleg", "email");
        userRepository.save(user);
        userRepository.delete(user.getId());
        assertThat(userRepository.findById(1L), not(is(user)));
    }

    @Test
    public void shouldFindById() throws Exception {
        User user = new User(1L, "Oleg", "email");
        userRepository.save(user);
        assertThat(userRepository.findById(1L), is(user));
    }

    @Test
    public void shouldReturnNullIfNotFoundedById() throws Exception {
        User user = userRepository.findById(10);
        assertEquals(null, user);
    }

    @Test
    public void shouldFindByEmail() throws Exception {
        User user = new User(1L, "Oleg", "email");
        userRepository.save(user);
        assertThat(userRepository.findByEmail("email"), is(user));
    }

    @Test
    public void shouldReturnNullIfNotFoundedByEmail() throws Exception {
        User user = userRepository.findByEmail("email10");
        assertEquals(null, user);
    }

    @Test
    public void shouldFindByName() throws Exception {
        User user1 = new User(1L, "Oleg", "email1");
        User user2 = new User(2L, "Oleg", "email2");
        userRepository.save(user1);
        userRepository.save(user2);
        User user = userRepository.findByName("Oleg");
        assertThat(user, is(user1));
    }

    @Test
    public void shouldReturnNullIfNotFoundedByName() throws Exception {
        User user = userRepository.findByName("user10");
        assertEquals(null, user);
    }

    @Test
    public void shouldGetAll() throws Exception {
        User user1 = new User(1L, "Oleg", "email1");
        User user2 = new User(2L, "Oleg", "email2");
        userRepository.save(user1);
        userRepository.save(user2);

        Collection<User> users = userRepository.getAll();
        assertEquals(2, users.size());
    }

    @After
    public void tearDown() throws Exception {
        userRepository.getAll().clear();
    }
}

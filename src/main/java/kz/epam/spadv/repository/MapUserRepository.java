package kz.epam.spadv.repository;

import kz.epam.spadv.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
public class MapUserRepository implements UserRepository {

    private Map<Long, User> users;
    private long id = 0L;

    @Autowired
    private TicketRepository ticketRepository;

    public MapUserRepository() {
        users = new HashMap<Long, User>();
    }

    @Override
    public User save(User user) {
        if (user == null) return null;
        if (user.getId() == null) {
            id++;
            user.setId(id);
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(long id) {
        ticketRepository.deleteBookedTicketByUserId(id);
        users.remove(id);
    }

    @Override
    public User findById(long id) {
        return users.get(id);
    }

    @Override
    public User findByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findByName(String name) {
        return users.values().stream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

}

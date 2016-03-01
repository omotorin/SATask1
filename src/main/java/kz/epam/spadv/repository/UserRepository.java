package kz.epam.spadv.repository;

import kz.epam.spadv.domain.User;

import java.util.Collection;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
public interface UserRepository {
    User save(User user);
    void delete(long id);
    User findById(long id);
    User findByEmail(String email);
    User findByName(String name);

    Collection<User> getAll();
}

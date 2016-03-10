package kz.epam.spadv.repository;

import kz.epam.spadv.domain.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
@Repository
public class JdbcUserAccountRepository implements UserAccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override public UserAccount create(long userId) {
        return null;
    }

    @Override public UserAccount getAccountByUserId(long userId) {
        return null;
    }

    @Override public UserAccount save(UserAccount account) {
        return null;
    }
}

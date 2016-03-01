package kz.epam.spadv.repository;

import kz.epam.spadv.utils.Convert;
import kz.epam.spadv.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg_Motorin on 27.10.2015.
 */
public class JdbcUserRepository implements UserRepository {
    private static final String UPDATE_USER_BY_ID = "UPDATE user SET name=?, email=?, birthDay=? WHERE id=?";
    private static final String UPDATE_USER_BY_NAME = "UPDATE user SET name=?, email=?, birthDay=? WHERE name=?";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM user WHERE id=?";
    private static final String SELECT_BY_USER_EMAIL = "SELECT * FROM user WHERE email=?";
    private static final String SELECT_BY_USER_NAME = "SELECT * FROM user WHERE name=?";
    private static final String DELETE_TICKETS = "DELETE FROM tickets WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM user";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private WinsRepository winsRepository;

    @Override
    public User save(User user) {
        if (user != null) {
            User updatedUser = null;
            if (user.getId() != null) {
                // try update users by id
                jdbcTemplate.update(UPDATE_USER_BY_ID,
                        user.getName(),
                        user.getEmail(),
                        Convert.toTimestamp(user.getBirthday()),
                        user.getId());
                updatedUser = findById(user.getId());
            } else if (user.getName() != null && !user.getName().isEmpty()) {
                // try update users by name
                jdbcTemplate.update(UPDATE_USER_BY_NAME,
                        user.getName(),
                        user.getEmail(),
                        Convert.toTimestamp(user.getBirthday()),
                        user.getName());
                updatedUser = findByName(user.getName());
            }
            if (updatedUser == null) {
                // insert if users not saved yet
                SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user");
                insert.setGeneratedKeyName("id");
                Map<String, Object> args = new HashMap<>();
                args.put("name", user.getName());
                args.put("email", user.getEmail());
                args.put("birthDay", Convert.toTimestamp(user.getBirthday()));
                user.setId(insert.executeAndReturnKey(args).longValue());
            } else {
                user = updatedUser;
            }
        }
        return user;
    }

    @Override
    public void delete(long id) {
        winsRepository.delete(id);
        jdbcTemplate.update(DELETE_TICKETS, id);
        jdbcTemplate.update(DELETE_USER, id);
    }

    @Override
    public User findById(long id) {
        User u;
        try {
            u = jdbcTemplate.queryForObject(SELECT_BY_USER_ID, new UserMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            u = null;
        }
        return u;
    }

    @Override
    public User findByEmail(String email) {
        User u;
        try {
            u = jdbcTemplate.queryForObject(SELECT_BY_USER_EMAIL, new UserMapper(), email);
        } catch (EmptyResultDataAccessException ex) {
            u = null;
        }
        return u;
    }

    @Override
    public User findByName(String name) {
        User u;
        try {
            u = jdbcTemplate.queryForObject(SELECT_BY_USER_NAME, new UserMapper(), name);
        } catch (EmptyResultDataAccessException ex) {
            u = null;
        }
        return u;
    }

    @Override
    public Collection<User> getAll() {
        Collection<User> users = jdbcTemplate.query(SELECT_ALL, new UserMapper());
        return users;
    }

    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            Date date = rs.getDate(4);
            User u = new User(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    date!=null ? date.toLocalDate() : null
            );

            return u;
        }
    }
}

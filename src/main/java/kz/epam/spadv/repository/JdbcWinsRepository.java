package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Win;
import kz.epam.spadv.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oleg_Motorin on 27.10.2015.
 */
public class JdbcWinsRepository implements WinsRepository {

    private static final String SELECT_ALL = "select w.*, u.* from wins w, user u WHERE u.id=w.user_id";
    private static final String SELECT_BY_USER_ID = "select * from wins w, user u WHERE w.user_id=u.id and u.id=?";
    private static final String DELETE_WINS = "DELETE from wins WHERE user_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Win save(Win win) {
        if (win != null && win.getUser() != null) {
            User user = win.getUser();
            if (user.getId() != null) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("wins");
                insert.setGeneratedKeyName("id");
                Map<String, Object> args = new HashMap<>();
                args.put("user_id", user.getId());
                args.put("date", win.getDate());
                win.setId(insert.executeAndReturnKey(args).longValue());
            }
        }
        return win;
    }

    @Override
    public List<Win> getAll() {
        List<Win> wins = jdbcTemplate.query(SELECT_ALL, new WinMapper());
        return wins;
    }

    @Override
    public List<Win> getByUserId(long userId) {
        List<Win> wins = jdbcTemplate.query(SELECT_BY_USER_ID, new WinMapper(), userId);
        return wins;
    }

    @Override
    public void delete(long userId) {
        jdbcTemplate.update(DELETE_WINS, userId);
    }

    private static final class WinMapper implements RowMapper<Win> {

        @Override
        public Win mapRow(ResultSet rs, int rowNum) throws SQLException {
            Win win = new Win();
            win.setId(rs.getLong(1));
            win.setDate(rs.getDate(3));
            User user = new User(rs.getLong(4), rs.getString(5), rs.getString(6), rs.getDate(7).toLocalDate());
            win.setUser(user);
            return win;
        }
    }
}

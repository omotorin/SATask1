package kz.epam.spadv.repository;

import kz.epam.spadv.domain.UserAccount;
import kz.epam.spadv.repository.exception.AccountAlreadyExistException;
import kz.epam.spadv.repository.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
@Repository
public class JdbcUserAccountRepository implements UserAccountRepository {

    private static final String UPDATE_ACCOUNT = "UPDATE account SET user_id = ?, amount = ? where id = ?";
    private static final String FIND_ACCOUNT_ID_BY_USER_ID = "SELECT id FROM account WHERE user_id=?";
    private static final String FIND_ACCOUNT_ID_BY_ID = "SELECT id FROM account WHERE id=?";
    private static final String FIND_ACCOUNT_BY_USER_ID = "SELECT * FROM account WHERE user_id = ?";
    private static final String DELETE_ACCOUNT_BY_ID = "DELETE FROM account WHERE id = ?";
    private static final String DELETE_ACCOUNT_BY_USER_ID = "DELETE FROM account WHERE user_id = ?";

    private final RowMapper<UserAccount> userAccountRowMapper = (rs, rowNum) -> {
        UserAccount acc = new UserAccount();
        acc.setId(rs.getLong("id"));
        acc.setUserId(rs.getLong("user_id"));
        acc.setAmount(rs.getFloat("amount"));
        return acc;
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserAccount create(long userId) throws AccountAlreadyExistException {
        UserAccount account = new UserAccount();
        account.setUserId(userId);

        if (jdbcTemplate.queryForObject(FIND_ACCOUNT_ID_BY_USER_ID, Long.TYPE, userId) != null) {
            throw new AccountAlreadyExistException();
        }

        SimpleJdbcInsertOperations insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("account");
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        account.setId(insert.executeAndReturnKey(params).longValue());

        return account;
    }

    @Override
    public UserAccount getAccountByUserId(long userId) throws AccountNotFoundException {
        try {
            return jdbcTemplate.queryForObject(FIND_ACCOUNT_BY_USER_ID, userAccountRowMapper, userId);
        } catch (EmptyResultDataAccessException ex){
            throw new AccountNotFoundException();
        }
    }

    @Override
    public void save(UserAccount account) throws AccountNotFoundException {
        if (account != null) {
            if (jdbcTemplate.queryForObject(FIND_ACCOUNT_ID_BY_ID, Long.TYPE, account.getId()) == null) {
                throw new AccountNotFoundException();
            }
            jdbcTemplate.update(UPDATE_ACCOUNT, account.getUserId(), account.getAmount(), account.getId());
        }
    }

    @Override
    public void delete(long accountId){
        jdbcTemplate.update(DELETE_ACCOUNT_BY_ID, accountId);
    }

    @Override
    public void deleteByUserID(long accountId){
        jdbcTemplate.update(DELETE_ACCOUNT_BY_USER_ID, accountId);
    }
}

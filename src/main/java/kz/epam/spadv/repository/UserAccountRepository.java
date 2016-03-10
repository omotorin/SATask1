package kz.epam.spadv.repository;

import kz.epam.spadv.domain.UserAccount;
import kz.epam.spadv.repository.exception.AccountAlreadyExistException;
import kz.epam.spadv.repository.exception.AccountNotFoundException;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
public interface UserAccountRepository {
    UserAccount create(long userId) throws AccountAlreadyExistException;
    UserAccount getAccountByUserId(long userId) throws AccountNotFoundException;
    void save(UserAccount account) throws AccountNotFoundException;

    void delete(long accountId);

    void deleteByUserID(long accountId);
}

package kz.epam.spadv.repository;

import kz.epam.spadv.domain.UserAccount;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
public interface UserAccountRepository {
    UserAccount create(long userId);
    UserAccount getAccountByUserId(long userId);
    UserAccount save(UserAccount account);
}

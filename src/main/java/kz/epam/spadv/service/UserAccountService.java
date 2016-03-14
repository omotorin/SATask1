package kz.epam.spadv.service;

import kz.epam.spadv.domain.UserAccount;
import kz.epam.spadv.repository.exception.AccountAlreadyExistException;
import kz.epam.spadv.repository.exception.AccountNotFoundException;
import kz.epam.spadv.service.exception.NotEnoughMoneyForWithdrawal;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
public interface UserAccountService {
    UserAccount create(long userId) throws AccountAlreadyExistException;
    boolean checkAmount(long accountId);

    UserAccount getAccountByUserId(long userId) throws AccountNotFoundException;

    void withdraw(long userId, float amountWithdraw) throws NotEnoughMoneyForWithdrawal, AccountNotFoundException;
    void refill(long userId, float amountDeposit) throws AccountNotFoundException;
}

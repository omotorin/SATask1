package kz.epam.spadv.service;

import kz.epam.spadv.domain.UserAccount;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
public interface UserAccountService {
    UserAccount create(Long userId);
    boolean checkAmount(Long accountId);
    void withdraw(Long accountId, float amountWithdraw);
    void deposit(float amountDeposit);
}

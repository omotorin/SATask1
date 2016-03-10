package kz.epam.spadv.service;

import kz.epam.spadv.domain.UserAccount;
import kz.epam.spadv.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository accountRepository;

    @Override public UserAccount create(Long userId) {
        return null;
    }

    @Override public boolean checkAmount(Long accountId) {
        return false;
    }

    @Override public void withdraw(Long accountId, float amountWithdraw) {

    }

    @Override public void deposit(float amountDeposit) {

    }
}

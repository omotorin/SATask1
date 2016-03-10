package kz.epam.spadv.service;

import kz.epam.spadv.domain.UserAccount;
import kz.epam.spadv.repository.UserAccountRepository;
import kz.epam.spadv.repository.exception.AccountAlreadyExistException;
import kz.epam.spadv.repository.exception.AccountNotFoundException;
import kz.epam.spadv.service.exception.NotEnoughMoneyForWithdrawal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository accountRepository;

    @Override
    public UserAccount create(long userId) throws AccountAlreadyExistException {
        return accountRepository.create(userId);
    }

    @Override
    public boolean checkAmount(long accountId) {
        return false;
    }

    @Override
    public void withdraw(long userId, float amountWithdraw) throws NotEnoughMoneyForWithdrawal, AccountNotFoundException {
        UserAccount account = accountRepository.getAccountByUserId(userId);
        if (account.getAmount() - amountWithdraw < 0) {
            throw new NotEnoughMoneyForWithdrawal();
        }
        account.setAmount(account.getAmount() - amountWithdraw);
        accountRepository.save(account);
    }

    @Override
    public void deposit(long userId, float amountDeposit) throws AccountNotFoundException {
        UserAccount account = accountRepository.getAccountByUserId(userId);
        account.setAmount(amountDeposit);
        accountRepository.save(account);
    }
}

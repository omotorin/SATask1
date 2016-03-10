package kz.epam.spadv.repository.exception;

/**
 * Created by olegm on 10.03.2016.
 */
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {
        super("Account not found!");
    }
}

package kz.epam.spadv.repository.exception;

/**
 * Created by olegm on 10.03.2016.
 */
public class AccountAlreadyExistException extends Exception {
    public AccountAlreadyExistException() {
        super("Account already exist!");
    }
}

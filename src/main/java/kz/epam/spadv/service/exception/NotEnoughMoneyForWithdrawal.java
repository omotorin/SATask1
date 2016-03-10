package kz.epam.spadv.service.exception;

/**
 * Created by olegm on 10.03.2016.
 */
public class NotEnoughMoneyForWithdrawal extends Exception {
    public NotEnoughMoneyForWithdrawal() {
        super("Not enough money for withdrawal!");
    }
}

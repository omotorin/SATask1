package kz.epam.spadv.domain;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
public class UserAccount {
    private Long id;
    private User user;
    private float amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}

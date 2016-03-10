package kz.epam.spadv.domain;

/**
 * Created by Oleg_Motorin on 3/10/2016.
 */
public class UserAccount {
    private Long id;
    private Long userId;
    private float amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}

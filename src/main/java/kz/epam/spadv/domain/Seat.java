package kz.epam.spadv.domain;

/**
 * Created by Oleg_Motorin on 02.11.2015.
 */
public class Seat {
    private int number;
    private boolean vip;

    public Seat(int number, boolean vip) {
        this.number = number;
        this.vip = vip;
    }

    public Seat(int number) {
        this.number = number;
        this.vip = false;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (number != seat.number) return false;
        return vip == seat.vip;

    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (vip ? 1 : 0);
        return result;
    }
}

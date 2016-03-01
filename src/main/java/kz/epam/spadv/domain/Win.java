package kz.epam.spadv.domain;

import java.util.Date;

/**
 * Created by Oleg_Motorin on 27.10.2015.
 */
public class Win {
    private Long id;
    private User user;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Win win = (Win) o;

        if (id != null ? !id.equals(win.id) : win.id != null) return false;
        if (user != null ? !user.equals(win.user) : win.user != null) return false;
        return !(date != null ? !date.equals(win.date) : win.date != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Win{" +
                "id=" + id +
                ", users=" + user +
                ", date=" + date +
                '}';
    }
}

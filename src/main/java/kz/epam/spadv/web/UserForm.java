package kz.epam.spadv.web;

/**
 * Created by Oleg_Motorin on 2/24/2016.
 */
public class UserForm {
    private String name;
    private String email;
    private String birthday;

    public UserForm(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}

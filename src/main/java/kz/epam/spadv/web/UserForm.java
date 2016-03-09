package kz.epam.spadv.web;

import kz.epam.spadv.domain.Role;
import kz.epam.spadv.service.Roles;

/**
 * Created by Oleg_Motorin on 2/24/2016.
 */
public class UserForm {
    private String name;
    private String email;
    private String birthday;
    private String password;
    private Roles role;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        Role role = new Role();
        role.setName(this.role.name());
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}

package kz.epam.spadv.domain;

import kz.epam.spadv.xml.LocalDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Oleg_Motorin on 21.10.2015.
 */
@XmlRootElement(name = "user")
@XmlType(propOrder = {"id","name","email","birthday","password","roles"})
public class User {

    private Long id;
    private String name;
    private String email;
    private LocalDate birthday;
    private String password;
    private List<Role> roles;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, LocalDate birthday) {
        this(name, email);
        this.birthday = birthday;
    }

    public User(long id, String name, String email, LocalDate birthday) {
        this(name, email, birthday);
        this.id = id;
    }

    public User() {

    }

    @XmlElement(required = true)
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlSchemaType(name = "date")
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @XmlElement(required = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement(required = true)
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null)
            return false;
        if (name != null ? !name.equals(user.name) : user.name != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        return roles != null ? roles.equals(user.roles) : user.roles == null;

    }

    @Override public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }
}

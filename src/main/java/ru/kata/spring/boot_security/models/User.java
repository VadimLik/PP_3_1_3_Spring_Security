package ru.kata.spring.boot_security.models;

import ru.kata.spring.boot_security.models.Role;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;


@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty(message = "Username не может быть пустым")
    @Size(min = 2, max = 30, message = "Username в пределах от 2 до 30 знаков")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 3, message = "Пароль содержит не менее трех знаков")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя в пределах от 2 до 30 знаков")
    @Column(name = "firstname")
    private String firstname;

    @NotEmpty(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 30, message = "Фамилия в пределах от 2 до 30 знаков")
    @Column(name = "lastname")
    private String lastName;

    @Min(value = 0, message = "Возраст не может быть менее 0")
    @Column(name = "age")
    private byte age;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {

    }

    public User(long id, String firstname, String lastName, byte age) {

        this.id = id;

        this.firstname = firstname;

        this.lastName = lastName;

        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getPassword() { return password; }

    public void setPassword (String password) { this.password = password; }

    public String getUserName() { return username; }

    public void setUserName(String username) { this.username = username; }

    public Collection<Role> getRoles() { return roles; }

    public void setRoles(Collection<Role> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

}

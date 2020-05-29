package org.netcracker.students.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * User entity
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    /**
     * Login of user
     */
    @Column(name = "login")
    private String login;

    /**
     * Password of user
     */
    @Column(name = "password")
    private String password;

    /**
     * Date when user was registered
     */
    @Column(name = "date_of_registration", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateOfRegistration;

    public User() {
    }

    public User(String login, String password, LocalDateTime dateOfRegistration) {
        this.login = login;
        this.password = password;
        this.dateOfRegistration = dateOfRegistration;
    }

    public User(int id, String login, String password, LocalDateTime dateOfRegistration) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.dateOfRegistration = dateOfRegistration;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getDateOfRegistration() {
        return dateOfRegistration;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setDateOfRegistration(LocalDateTime dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public void setId(int id) {
        this.id = id;
    }
}

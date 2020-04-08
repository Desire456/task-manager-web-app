package org.netcracker.students.model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String login;
    private String password;
    private String role;
    private LocalDateTime dateOfRegistration;

    public User(String login, String password, String role, LocalDateTime dateOfRegistration) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.dateOfRegistration = dateOfRegistration;
    }

    public User(int id, String login, String password, String role, LocalDateTime dateOfRegistration) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.dateOfRegistration = dateOfRegistration;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                '}';
    }

    //region Getters
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

    public String getRole() {
        return role;
    }
    //endregion

    //region Setters
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

    public void setRole(String role) {
        this.role = role;
    }
    //endregion

}

package org.netcracker.students.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private int id;
    private String login;
    private String password;
    private String role;
    private LocalDate dateOfRegistration;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, String role, LocalDate dateOfRegistration){
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.dateOfRegistration = dateOfRegistration;
    }

    //region Getters
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public LocalDate getDateOfRegistration() {
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

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
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

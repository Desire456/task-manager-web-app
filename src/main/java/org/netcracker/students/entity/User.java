package org.netcracker.students.entity;

import java.sql.Date;
import java.time.LocalDateTime;

public class User {
    private int userId;
    private String login;
    private String password;
    private String role;
    private Date dateOfRegistration;

    public User(){}

    public User(int userID, String login, String password, String role){
        this.login = login;
        this.userId = userID;
        this.password = password;
        this.role = role;
    }

    public User(int userID, String login, String password, String role, Date dateOfRegistration){
        this.login = login;
        this.userId = userID;
        this.password = password;
        this.role = role;
        this.dateOfRegistration = dateOfRegistration;
    }
    //region Getters
    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() { return role;}
    //endregion

    //region Setters
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRole (String role) { this.role = role;}
    //endregion
}

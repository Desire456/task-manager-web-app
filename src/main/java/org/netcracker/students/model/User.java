package org.netcracker.students.model;

import java.time.LocalDate;

public class User {
    private int id;
    private String login;
    private String password;
    private LocalDate dateOfRegistration;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
}

package org.netcracker.students.factories;

import org.netcracker.students.model.User;

import java.time.LocalDate;

public class UserFactory {
    public User createUser(int id, String login, String password, String role, LocalDate dateOfRegistration) {
        return new User(id, login, password, role, dateOfRegistration);
    }

    public User createUser(String login, String password, String role, LocalDate dateOfRegistration) {
        return new User(login, password, role, dateOfRegistration);
    }
}

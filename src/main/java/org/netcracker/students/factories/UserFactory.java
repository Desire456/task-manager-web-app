package org.netcracker.students.factories;

import org.netcracker.students.model.User;

import java.time.LocalDateTime;

public class UserFactory {
    public static User createUser(int id, String login, String password, String role, LocalDateTime dateOfRegistration) {
        return new User(id, login, password, role, dateOfRegistration);
    }

    public static User createUser(String login, String password, String role, LocalDateTime dateOfRegistration) {
        return new User(login, password, role, dateOfRegistration);
    }
}
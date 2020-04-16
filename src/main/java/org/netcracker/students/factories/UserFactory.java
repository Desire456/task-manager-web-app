package org.netcracker.students.factories;

import org.netcracker.students.model.User;

import java.time.LocalDateTime;

public class UserFactory {
    public static User createUser(int id, String login, String password, LocalDateTime dateOfRegistration) {
        return new User(id, login, password, dateOfRegistration);
    }

    public static User createUser(String login, String password, LocalDateTime dateOfRegistration) {
        return new User(login, password, dateOfRegistration);
    }
}
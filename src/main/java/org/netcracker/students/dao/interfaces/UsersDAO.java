package org.netcracker.students.dao.interfaces;


import org.netcracker.students.dao.exceptions.userDAO.*;
import org.netcracker.students.model.User;

import java.sql.Timestamp;
import java.util.List;

public interface UsersDAO {
    User create(String login, String password, Timestamp dateOfRegistration) throws CreateUserException;

    User read(int id) throws ReadUserException;

    void update(User user) throws UpdateUserException;

    void delete(int userId) throws DeleteUserException;

    List<User> getAll() throws GetAllUserException;

    User getByLoginAndPassword(String login, String password) throws GetUserByLoginAndPasswordException;

    User getByLogin(String login) throws GetUserByLoginException;

    List<User> getSortedByCriteria(String column, String criteria) throws GetSortedByCriteriaUser;
}

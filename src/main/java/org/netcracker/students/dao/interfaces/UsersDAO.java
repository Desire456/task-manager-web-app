package org.netcracker.students.dao.interfaces;


import org.netcracker.students.dao.exception.userDAO.*;
import org.netcracker.students.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface UsersDAO {
    public User create(String login, String password, String role, Timestamp dateOfRegistration) throws CreateUserException;

    public User read(int id) throws ReadUserException;

    public void update(User user) throws UpdateUserException;

    public void delete(int userId) throws  DeleteUserException;

    public List<User> getAll() throws GetAllUserException;

    public User getByLoginAndPassword(String login, String password) throws GetUserByLoginAndPasswordException;

    public User getByLogin(String login) throws GetUserByLoginException;

    public List<User> getSortedByCriteria(String column, String criteria) throws GetSortedByCriteriaUser;
}

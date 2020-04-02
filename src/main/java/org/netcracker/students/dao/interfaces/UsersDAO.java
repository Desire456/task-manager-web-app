package org.netcracker.students.dao.interfaces;


import org.netcracker.students.dao.exception.userDAO.*;
import org.netcracker.students.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface UsersDAO {
    public User create(String login, String password, String role, Date dateOfRegistration) throws SQLException, CreateUserException;

    public User read(int id) throws SQLException, ReadUserException;

    public void update(User user) throws SQLException, UpdateUserException;

    public void delete(int userId) throws SQLException, DeleteUserException;

    public List<User> getAll() throws SQLException, GetAllUserException;

    public User getByLoginAndPassword(String login, String password) throws SQLException, GetUserByLoginAndPasswordException;

    public User getByLogin(String login) throws SQLException, GetUserByLoginException;

    public List<User> getSortedByCriteria(String column, String criteria) throws SQLException, GetSortedByCriteriaUser;
}

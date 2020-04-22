package org.netcracker.students.controller;

import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.userDAO.*;
import org.netcracker.students.dao.interfaces.ManagerDAO;
import org.netcracker.students.dao.interfaces.UsersDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;
import org.netcracker.students.dao.postrgresql.PostgreSQLManagerDAO;
import org.netcracker.students.model.User;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class UserController {
    private static UserController instance;
    private UsersDAO usersDAO;

    public static synchronized UserController getInstance() throws GetConnectionException {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    private UserController() throws GetConnectionException {
        ManagerDAO ManagerDAO = PostgreSQLManagerDAO.getInstance();
        try {
            usersDAO = ManagerDAO.getUsersDao();
        } catch (SQLException e) {
            throw new GetConnectionException(DAOErrorConstants.GET_CONNECTION_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    public User getUser(int id) throws ReadUserException {
        return usersDAO.read(id);
    }

    public User getUserByLogin(String login) throws GetUserByLoginException {
        return usersDAO.getByLogin(login);
    }

    public User getUserByLoginAndPassword(String login, String password) throws GetUserByLoginAndPasswordException {
        return usersDAO.getByLoginAndPassword(login, password);
    }

    public void addUser(User user) throws CreateUserException {
        usersDAO.create(user.getLogin(), user.getPassword(), Timestamp.valueOf(user.getDateOfRegistration()));
    }

    public void deleteUser(int id) throws DeleteUserException {
        usersDAO.delete(id);
    }

    public void changeUser(User user) throws UpdateUserException {
        usersDAO.update(user);
    }

    public List<User> getAll() throws GetAllUserException {
        return usersDAO.getAll();
    }
}
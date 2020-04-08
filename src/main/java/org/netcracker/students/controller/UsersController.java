package org.netcracker.students.controller;

import org.netcracker.students.dao.exceptions.userDAO.*;
import org.netcracker.students.dao.interfaces.DAOManager;
import org.netcracker.students.dao.interfaces.UsersDAO;
import org.netcracker.students.dao.postrgresql.PostgreSQLDAOManager;
import org.netcracker.students.model.User;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class UsersController {
    private static UsersController instance;
    private UsersDAO usersDAO;

    public static synchronized UsersController getInstance() {
        if (instance == null) {
            instance = new UsersController();
        }
        return instance;
    }

    private UsersController() {
        DAOManager DAOManager = PostgreSQLDAOManager.getInstance();
        try {
            usersDAO = DAOManager.getUsersDao();
        } catch (SQLException e) {
            e.printStackTrace();
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
        usersDAO.create(user.getLogin(), user.getPassword(), user.getRole(), Timestamp.valueOf(user.getDateOfRegistration()));
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

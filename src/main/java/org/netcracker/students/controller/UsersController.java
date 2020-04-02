package org.netcracker.students.controller;

import org.netcracker.students.dao.exception.userDAO.*;
import org.netcracker.students.dao.interfaces.DAOManager;
import org.netcracker.students.dao.interfaces.UsersDAO;
import org.netcracker.students.dao.postrgresql.PostgreSQLDAOManager;
import org.netcracker.students.model.User;

import java.sql.Date;
import java.sql.SQLException;
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

    public User getUser(int id) throws SQLException, ReadUserException {
        return usersDAO.read(id);
    }

    public User getUser(String login) throws SQLException, GetUserByLoginException {
        return usersDAO.getByLogin(login);
    }

    public void addUser(User user) throws SQLException, CreateUserException {
        usersDAO.create(user.getLogin(), user.getPassword(), user.getRole(), Date.valueOf(user.getDateOfRegistration()));
    }

    public void deleteUser(int id) throws SQLException, DeleteUserException {
        usersDAO.delete(id);
    }

    public void changeUser(User user) throws SQLException, UpdateUserException {
        usersDAO.update(user);
    }

    public List<User> getAll() throws SQLException, GetAllUserException {
        return usersDAO.getAll();
    }
}

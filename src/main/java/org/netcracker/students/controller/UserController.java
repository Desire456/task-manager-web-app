package org.netcracker.students.controller;

import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.userDAO.*;
import org.netcracker.students.dao.hibernate.HibernateManagerDAO;
import org.netcracker.students.dao.interfaces.ManagerDAO;
import org.netcracker.students.dao.interfaces.UsersDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;
import org.netcracker.students.dao.postrgresql.PostgreSQLManagerDAO;
import org.netcracker.students.model.User;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Class to control the users hiding the DAO layer
 * @see User
 */
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

    /**
     * Get user by id
     * @param id
     * @return
     * @throws ReadUserException can't get user by this id or problems with connection to DB
     */
    public User getUser(int id) throws ReadUserException {
        return usersDAO.read(id);
    }

    /**
     * Get user by login
     * @param login
     * @return
     * @throws GetUserByLoginException can't get user by this login or problems with connection to DB
     */
    public User getUserByLogin(String login) throws GetUserByLoginException {
        return usersDAO.getByLogin(login);
    }

    /**
     * Get user by login and password
     * @param login
     * @param password
     * @return
     * @throws GetUserByLoginAndPasswordException can't get user by this login and password or problems
     *                                                                                  with connection to DB
     */
    public User getUserByLoginAndPassword(String login, String password) throws GetUserByLoginAndPasswordException {
        return usersDAO.getByLoginAndPassword(login, password);
    }

    /**
     * Add new user
     * @param user
     * @throws CreateUserException can't add this user or problems with connection to DB
     */
    public void addUser(User user) throws CreateUserException {
        usersDAO.create(user.getLogin(), user.getPassword(), Timestamp.valueOf(user.getDateOfRegistration()));
    }

    /**
     * Delete user by id
     * @param id
     * @throws DeleteUserException can't delete user by this id or problems with connection to DB
     */
    public void deleteUser(int id) throws DeleteUserException {
        usersDAO.delete(id);
    }

    /**
     * Update old user to new user
     * @param user
     * @throws UpdateUserException can't update to new user or problems with connection to DB
     */
    public void changeUser(User user) throws UpdateUserException {
        usersDAO.update(user);
    }

    /**
     * Get all users
     * @return
     * @throws GetAllUserException can't get all users or problems with connection to DB
     */
    public List<User> getAll() throws GetAllUserException {
        return usersDAO.getAll();
    }
}
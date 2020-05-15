package org.netcracker.students.dao.hibernate;

import org.netcracker.students.dao.exceptions.userDAO.*;
import org.netcracker.students.dao.interfaces.UsersDAO;
import org.netcracker.students.model.User;

import java.sql.Timestamp;
import java.util.List;

public class HibernateUserDAO implements UsersDAO {
    @Override
    public User create(String login, String password, Timestamp dateOfRegistration) throws CreateUserException {
        return null;
    }

    @Override
    public User read(int id) throws ReadUserException {
        return null;
    }

    @Override
    public void update(User user) throws UpdateUserException {

    }

    @Override
    public void delete(int userId) throws DeleteUserException {

    }

    @Override
    public List<User> getAll() throws GetAllUserException {
        return null;
    }

    @Override
    public User getByLoginAndPassword(String login, String password) throws GetUserByLoginAndPasswordException {
        return null;
    }

    @Override
    public User getByLogin(String login) throws GetUserByLoginException {
        return null;
    }

    @Override
    public List<User> getSortedByCriteria(String column, String criteria) throws GetSortedByCriteriaUser {
        return null;
    }
}

package org.netcracker.students.dao.hibernate;

import org.hibernate.*;
import org.netcracker.students.dao.exceptions.userDAO.*;
import org.netcracker.students.dao.interfaces.UsersDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;
import org.netcracker.students.factories.UserFactory;
import org.netcracker.students.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HibernateUserDAO implements UsersDAO {
    private final SessionFactory sessionFactory;

    public HibernateUserDAO(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    @Override
    public User create(String login, String password, Timestamp dateOfRegistration) throws CreateUserException {
        User user;
        try {
            user = UserFactory.createUser(login, password, dateOfRegistration.toLocalDateTime());
            Session session = sessionFactory.openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new CreateUserException(DAOErrorConstants.CREATE_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
        return user;
    }

    @Override
    public User read(int id) throws ReadUserException {
        User user;
        try {
            user = sessionFactory.openSession().get(User.class, id);
        } catch (HibernateException e) {
            throw new ReadUserException(DAOErrorConstants.READ_JOURNAL_EXCEPTION + e.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user) throws UpdateUserException {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx1 = session.beginTransaction();
            session.update(user);
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new UpdateUserException(DAOErrorConstants.UPDATE_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public void delete(int userId) throws DeleteUserException {
        try {
            Session session = sessionFactory.openSession();
            String hql = "Delete From User where user_id = :user_id";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.USER_ID, userId);
            query.executeUpdate();
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new DeleteUserException(DAOErrorConstants.DELETE_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() throws GetAllUserException {
        List<User> users = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From User";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            for (Object o : query.list()) {
                users.add((User) o);
            }
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new GetAllUserException(DAOErrorConstants.GET_ALL_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
        return users;
    }

    @Override
    public User getByLoginAndPassword(String login, String password) throws GetUserByLoginAndPasswordException {
        List<User> users = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From User where login = :login and password = :password";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.LOGIN, login);
            query.setParameter(HibernateDAOConstants.PASSWORD, password);
            for (Object o : query.list()) {
                users.add((User) o);
            }
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new GetUserByLoginAndPasswordException(DAOErrorConstants.GET_USER_BY_LOGIN_AND_PASSWORD_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User getByLogin(String login) throws GetUserByLoginException {
        List<User> users = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From User where login = :login";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.LOGIN, login);
            for (Object o : query.list()) {
                users.add((User) o);
            }
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new GetUserByLoginException(DAOErrorConstants.GET_USER_BY_LOGIN_EXCEPTION_MESSAGE +
                    e.getMessage());
        }
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> getSortedByCriteria(String column, String criteria) throws GetSortedByCriteriaUser {
        List<User> users = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From User order by %s %s";
            hql = String.format(hql, column, criteria);
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            for (Object o : query.list()) {
                users.add((User) o);
            }
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new GetSortedByCriteriaUser(DAOErrorConstants.GET_SORTED_BY_CRITERIA_USER_EXCEPTION_MESSAGE +
                    e.getMessage());
        }
        return users;
    }
}
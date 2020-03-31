package org.netcracker.students.dao.interfaces;


import org.netcracker.students.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface UsersDAO {
    public User create(String login, String password, String role, Date dateOfRegistration) throws SQLException;

    public User read(int id) throws SQLException;

    public void update(User user) throws SQLException;

    public void delete(int userId) throws SQLException;

    public List<User> getAll() throws SQLException;

    public User getByLoginAndPassword(String login, String password) throws SQLException;

    public User getByLogin(String login) throws SQLException;

    public List<User> getSortedByCriteria(String column, String criteria) throws SQLException;
}

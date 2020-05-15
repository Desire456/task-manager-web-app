package org.netcracker.students.dao.hibernate;

import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.interfaces.ManagerDAO;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.interfaces.UsersDAO;

import java.sql.SQLException;

public class HibernateManagerDAO implements ManagerDAO {
    @Override
    public TasksDAO getTasksDao() throws SQLException {
        return null;
    }

    @Override
    public JournalDAO getJournalDao() throws SQLException {
        return null;
    }

    @Override
    public UsersDAO getUsersDao() throws SQLException {
        return null;
    }
}

package org.netcracker.students.dao.interfaces;

import java.sql.SQLException;

public interface DAOManager {
    public TasksDAO getTasksDao() throws SQLException;

    public JournalDAO getJournalDao() throws SQLException;

    public UsersDAO getUsersDao() throws SQLException;
}

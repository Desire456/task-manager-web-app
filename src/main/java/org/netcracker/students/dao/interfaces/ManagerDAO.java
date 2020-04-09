package org.netcracker.students.dao.interfaces;

import java.sql.SQLException;

public interface ManagerDAO {
    TasksDAO getTasksDao() throws SQLException;

    JournalDAO getJournalDao() throws SQLException;

    UsersDAO getUsersDao() throws SQLException;
}

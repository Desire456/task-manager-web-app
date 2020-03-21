package org.netcracker.students.dao.interfaces;

public interface DAOManager {
    public TasksDAO getTasksDao();

    public JournalDAO getJournalDao();

    public UsersDAO getUsersDao();
}

package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.dao.connection.ConnectionBuilder;
import org.netcracker.students.dao.connection.PoolConnectionBuilder;
import org.netcracker.students.dao.interfaces.DAOManager;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.interfaces.UsersDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgreSQLDAOManager implements DAOManager {
    private static PostgreSQLDAOManager instance;
    ConnectionBuilder connectionBuilder;

    private PostgreSQLDAOManager(String path) throws SQLException {
        connectionBuilder = new PoolConnectionBuilder(path);
    }

    private PostgreSQLDAOManager(){}

    public static PostgreSQLDAOManager getInstance(){
        if (instance == null)
            instance = new PostgreSQLDAOManager();
        return instance;
    }

    public static PostgreSQLDAOManager getInstance(String path) throws SQLException {
        if (instance == null)
            instance = new PostgreSQLDAOManager(path);
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return connectionBuilder.getConnect();
    }

    @Override
    public TasksDAO getTasksDao() {
        return null; //todo доделать
    }

    @Override
    public JournalDAO getJournalDao() {
        return null;
    }

    @Override
    public UsersDAO getUsersDao() {
        return null;
    }
}

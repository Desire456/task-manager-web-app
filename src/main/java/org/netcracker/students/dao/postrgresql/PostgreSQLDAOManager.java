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
        connectionBuilder = new PoolConnectionBuilder();
        executeSqlStartScript(path);
    }

    private PostgreSQLDAOManager(){
        connectionBuilder = new PoolConnectionBuilder();
    }

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
    public TasksDAO getTasksDao() throws SQLException {
        return new PostgreSQLTasksDAO(getConnection());
    }

    @Override
    public JournalDAO getJournalDao() {
        return new PostgreSQLJournalDAO();
    }

    @Override
    public UsersDAO getUsersDao() {
        return new PostgreSQLUsersDAO();
    }

    private void executeSqlStartScript(String path) {
        //todo Илья, вот здесь скрипт нужно запускать как я понимаю
    }
}
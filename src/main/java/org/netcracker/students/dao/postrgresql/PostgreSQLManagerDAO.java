package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.dao.connection.ConnectionBuilder;
import org.netcracker.students.dao.connection.PoolConnectionBuilder;
import org.netcracker.students.dao.exceptions.managerDAO.ExecuteSqlScriptException;
import org.netcracker.students.dao.interfaces.ManagerDAO;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.interfaces.UsersDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLManagerDAO implements ManagerDAO {
    private static PostgreSQLManagerDAO instance;
    private ConnectionBuilder connectionBuilder;

    private PostgreSQLManagerDAO(String path) throws ExecuteSqlScriptException {
        connectionBuilder = new PoolConnectionBuilder();
        executeSqlStartScript(path);
    }

    private PostgreSQLManagerDAO() {
        connectionBuilder = new PoolConnectionBuilder();
    }

    public static PostgreSQLManagerDAO getInstance() {
        if (instance == null)
            instance = new PostgreSQLManagerDAO();
        return instance;
    }

    public static PostgreSQLManagerDAO getInstance(String path) throws ExecuteSqlScriptException {
        if (instance == null)
            instance = new PostgreSQLManagerDAO(path);
        return instance;
    }

    public Connection getConnection() throws SQLException {
            return connectionBuilder.getConnect();
    }

    @Override
    public TasksDAO getTasksDao() throws SQLException {
        return new PostgreSQLTaskDAO(getConnection());
    }

    @Override
    public JournalDAO getJournalDao() throws SQLException {
        return new PostgreSQLJournalDAO(getConnection());
    }

    @Override
    public UsersDAO getUsersDao() throws SQLException {
        return new PostgreSQLUserDAO(getConnection());
    }

    private void executeSqlStartScript(String path) throws ExecuteSqlScriptException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Statement statement = connectionBuilder.getConnect().createStatement();
                statement.execute(line);
            }
        } catch (SQLException | IOException e) {
            throw new ExecuteSqlScriptException(DAOErrorConstants.EXECUTE_SQL_SCRIPT_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
    }
}
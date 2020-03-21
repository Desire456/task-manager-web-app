package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.dao.interfaces.DAOManager;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.interfaces.UsersDAO;

import java.sql.Connection;

public class PostgreSQLDAOManager implements DAOManager {
    private static PostgreSQLDAOManager instance;
    private Connection connection;

    private PostgreSQLDAOManager(String path){

    }

    private PostgreSQLDAOManager(){}

    public static PostgreSQLDAOManager getInstance(){
        if (instance == null)
            instance = new PostgreSQLDAOManager();
        return instance;
    }

    public static PostgreSQLDAOManager getInstance(String path){
        if (instance == null)
            instance = new PostgreSQLDAOManager(path);
        return instance;
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

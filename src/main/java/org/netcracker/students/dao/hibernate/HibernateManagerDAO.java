package org.netcracker.students.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.netcracker.students.dao.exceptions.managerDAO.ExecuteSqlScriptException;
import org.netcracker.students.dao.hibernate.utils.HibernateSessionFactoryUtil;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.interfaces.ManagerDAO;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.interfaces.UsersDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class HibernateManagerDAO implements ManagerDAO {
    private static HibernateManagerDAO instance;

    private HibernateManagerDAO(String path) throws ExecuteSqlScriptException {
        executeSqlStartScript(path);
    }

    private HibernateManagerDAO() {

    }

    public static HibernateManagerDAO getInstance() {
        if (instance == null)
            instance = new HibernateManagerDAO();
        return instance;
    }

    public static HibernateManagerDAO getInstance(String path) throws ExecuteSqlScriptException {
        if (instance == null)
            instance = new HibernateManagerDAO(path);
        return instance;
    }

    @Override
    public TasksDAO getTasksDao() throws SQLException {
        return null;
    }

    @Override
    public JournalDAO getJournalDao() throws SQLException {
        return new HibernateJournalDAO();
    }

    @Override
    public UsersDAO getUsersDao() throws SQLException {
        return null;
    }

    private void executeSqlStartScript(String path) throws ExecuteSqlScriptException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            StringBuilder query = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while ((line = br.readLine()) != null) {
                query.append(line);
                query.append(ls);
            }
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery(query.toString());
            sqlQuery.executeUpdate();
            tx.commit();
            session.close();
        } catch (IOException | HibernateException e) {
            throw new ExecuteSqlScriptException(DAOErrorConstants.EXECUTE_SQL_SCRIPT_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
    }
}

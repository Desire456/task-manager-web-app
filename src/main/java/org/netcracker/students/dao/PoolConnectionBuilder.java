package org.netcracker.students.dao;

import org.netcracker.students.dao.interfaces.ConnectionBuilder;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilder implements ConnectionBuilder {

    private DataSource dataSource;

    public PoolConnectionBuilder() {
        try{
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/taskManager");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnect() throws SQLException {
        return dataSource.getConnection();
    }
}

package org.netcracker.students.dao.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilder implements ConnectionBuilder {
    private static final String JDBC_DB_NAME = "java:comp/env/jdbc/taskManager";

    private DataSource dataSource;

    public PoolConnectionBuilder() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(JDBC_DB_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnect() throws SQLException {
        return dataSource.getConnection();
    }
}

package org.netcracker.students.dao.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilder implements ConnectionBuilder {

    private DataSource dataSource;

    public PoolConnectionBuilder(String path) {
        try{
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(path);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnect() throws SQLException {
        return dataSource.getConnection();
    }
}

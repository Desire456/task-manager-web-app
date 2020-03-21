package org.netcracker.students.controller.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectorDB {
    public Connection getConnection(String url, String user, String password) throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("databaseConnection");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        String dbName = resource.getString("db.name");
        return DriverManager.getConnection(url+dbName, user, pass);
    }

    public Connection getConnectionWithUser() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("databaseConnection");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        return DriverManager.getConnection(url, user, pass);
    }


}

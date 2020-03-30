package org.netcracker.students;

import org.netcracker.students.dao.postrgresql.PostgreSQLDAOManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        PostgreSQLDAOManager postgreSQLDAOManager = PostgreSQLDAOManager.getInstance("asd");
    }
}

package org.netcracker.students;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("SELECT * FROM journals WHERE ? LIKE '?' ORDER BY ? ?");
    }
}

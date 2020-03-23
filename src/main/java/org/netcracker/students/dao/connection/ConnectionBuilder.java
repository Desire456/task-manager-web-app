package org.netcracker.students.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionBuilder {
    Connection getConnect() throws SQLException;
}

package org.netcracker.students.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionBuilder {
    Connection getConnect() throws SQLException;
}

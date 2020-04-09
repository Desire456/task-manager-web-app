package org.netcracker.students.dao.exceptions.managerDAO;

public class ExecuteSqlScriptException extends Exception {
    public ExecuteSqlScriptException() {
        super();
    }

    public ExecuteSqlScriptException(String message) {
        super(message);
    }

    public ExecuteSqlScriptException(Throwable cause) {
        super(cause);
    }
}

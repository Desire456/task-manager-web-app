package org.netcracker.students.dao.exceptions.managerDAO;

public class GetConnectionException extends Exception {
    public GetConnectionException() {
        super();
    }

    public GetConnectionException(String message) {
        super(message);
    }

    public GetConnectionException(Throwable cause) {
        super(cause);
    }
}

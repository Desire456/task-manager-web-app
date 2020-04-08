package org.netcracker.students.dao.exceptions.userDAO;

public class GetUserByLoginAndPasswordException extends Exception {
    public GetUserByLoginAndPasswordException() {
        super();
    }

    public GetUserByLoginAndPasswordException(String message) {
        super(message);
    }

    public GetUserByLoginAndPasswordException(Throwable e) {
        super(e);
    }
}

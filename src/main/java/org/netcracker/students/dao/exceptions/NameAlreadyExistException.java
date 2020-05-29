package org.netcracker.students.dao.exceptions;

public class NameAlreadyExistException extends Exception{
    public NameAlreadyExistException() {
    }

    public NameAlreadyExistException(String message) {
        super(message);
    }

    public NameAlreadyExistException(Throwable cause) {
        super(cause);
    }
}

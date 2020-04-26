package org.netcracker.students.controller.utils.hashing;

public class HashPasswordException extends Exception{
    public HashPasswordException() {
        super();
    }

    public HashPasswordException(String message) {
        super(message);
    }

    public HashPasswordException(Throwable cause) {
        super(cause);
    }
}

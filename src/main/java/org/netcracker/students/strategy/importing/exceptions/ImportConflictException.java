package org.netcracker.students.strategy.importing.exceptions;

public class ImportConflictException extends Exception{
    public ImportConflictException() {
    }

    public ImportConflictException(String message) {
        super(message);
    }

    public ImportConflictException(Throwable cause) {
        super(cause);
    }
}

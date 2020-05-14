package org.netcracker.students.ejb.exceptions;

public class ExportException extends Exception{
    public ExportException() {
    }

    public ExportException(String message) {
        super(message);
    }

    public ExportException(Throwable cause) {
        super(cause);
    }
}

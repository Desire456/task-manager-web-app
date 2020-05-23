package org.netcracker.students.strategy.importing.exceptions;

/**
 * Exception that indicates an important import error
 */
public class PrintableImportException extends Exception{
    public PrintableImportException() {
    }

    public PrintableImportException(String message) {
        super(message);
    }

    public PrintableImportException(Throwable cause) {
        super(cause);
    }
}

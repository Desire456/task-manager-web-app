package org.netcracker.students.strategy.exporting.exceptions;

/**
 * Exception that indicates an important export error
 */
public class PrintableExportException extends Exception{
    public PrintableExportException() {
    }

    public PrintableExportException(String message) {
        super(message);
    }

    public PrintableExportException(Throwable cause) {
        super(cause);
    }
}

package org.netcracker.students.strategy.importing.exceptions;

/**
 * Indicates a common import error
 */
public class ImportException extends Exception {
    public ImportException() {
    }

    public ImportException(String message) {
        super(message);
    }

    public ImportException(Throwable cause) {
        super(cause);
    }
}

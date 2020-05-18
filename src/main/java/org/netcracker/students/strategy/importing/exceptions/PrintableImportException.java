package org.netcracker.students.strategy.importing.exceptions;

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

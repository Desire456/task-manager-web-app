package org.netcracker.students.strategy.importing.exceptions;

public class ImportJournalNotFoundException extends Exception{
    public ImportJournalNotFoundException() {
        super();
    }

    public ImportJournalNotFoundException(String message) {
        super(message);
    }

    public ImportJournalNotFoundException(Throwable cause) {
        super(cause);
    }
}

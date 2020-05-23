package org.netcracker.students.strategy.importing.exceptions;

/**
 * Indicates lack of journal for import task
 */
public class ImportJournalNotFoundException extends Exception {
    public ImportJournalNotFoundException() {
    }

    public ImportJournalNotFoundException(String message) {
        super(message);
    }

    public ImportJournalNotFoundException(Throwable cause) {
        super(cause);
    }
}

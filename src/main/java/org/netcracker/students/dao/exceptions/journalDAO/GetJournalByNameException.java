package org.netcracker.students.dao.exceptions.journalDAO;

public class GetJournalByNameException extends Exception {
    public GetJournalByNameException() {
        super();
    }

    public GetJournalByNameException(String message) {
        super(message);
    }

    public GetJournalByNameException(Throwable cause) {
        super(cause);
    }
}

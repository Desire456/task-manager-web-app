package org.netcracker.students.dao.exception.taskDAO;

public class GetAllTaskByJournalIdException extends Exception {

    public GetAllTaskByJournalIdException(String message) {
        super(message);
    }

    public GetAllTaskByJournalIdException() {
        super();
    }

    public GetAllTaskByJournalIdException(Throwable e) {
        super(e);
    }
}

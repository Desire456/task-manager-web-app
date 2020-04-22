package org.netcracker.students.dao.exceptions.taskDAO;

public class DeleteTaskException extends Exception {
    public DeleteTaskException(String message) {
        super(message);
    }

    public DeleteTaskException() {
        super();
    }

    public DeleteTaskException(Throwable e) {
        super(e);
    }
}

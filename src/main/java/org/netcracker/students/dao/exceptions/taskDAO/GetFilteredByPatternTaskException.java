package org.netcracker.students.dao.exceptions.taskDAO;

public class GetFilteredByPatternTaskException extends Exception {
    public GetFilteredByPatternTaskException(String message) {
        super(message);
    }

    public GetFilteredByPatternTaskException() {
        super();
    }

    public GetFilteredByPatternTaskException(Throwable e) {
        super(e);
    }
}

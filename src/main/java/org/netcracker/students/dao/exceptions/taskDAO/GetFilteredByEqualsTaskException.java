package org.netcracker.students.dao.exceptions.taskDAO;

public class GetFilteredByEqualsTaskException extends Exception {
    public GetFilteredByEqualsTaskException(String message) {
        super(message);
    }

    public GetFilteredByEqualsTaskException() {
        super();
    }

    public GetFilteredByEqualsTaskException(Throwable e) {
        super(e);
    }
}

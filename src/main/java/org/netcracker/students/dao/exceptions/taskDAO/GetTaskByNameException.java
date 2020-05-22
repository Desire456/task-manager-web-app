package org.netcracker.students.dao.exceptions.taskDAO;

public class GetTaskByNameException extends Exception{
    public GetTaskByNameException() {
        super();
    }

    public GetTaskByNameException(String message) {
        super(message);
    }

    public GetTaskByNameException(Throwable cause) {
        super(cause);
    }
}

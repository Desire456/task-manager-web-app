package org.netcracker.students.dao.exception.taskDAO;

public class UpdateTaskException extends Exception {
    public UpdateTaskException(String message){
        super(message);
    }

    public UpdateTaskException(){
        super();
    }

    public UpdateTaskException(Throwable e){
        super(e);
    }
}

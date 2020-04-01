package org.netcracker.students.dao.exception.taskDAO;

public class CreateTaskException extends Exception {
    public CreateTaskException(String message){
        super(message);
    }

    public CreateTaskException(){
        super();
    }

    public CreateTaskException(Throwable e){
        super(e);
    }
}

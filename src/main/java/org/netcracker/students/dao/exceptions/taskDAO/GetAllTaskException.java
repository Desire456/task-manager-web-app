package org.netcracker.students.dao.exceptions.taskDAO;

public class GetAllTaskException extends Exception {
    public GetAllTaskException(String message){
        super(message);
    }

    public GetAllTaskException(){
        super();
    }

    public GetAllTaskException(Throwable e){
        super(e);
    }
}

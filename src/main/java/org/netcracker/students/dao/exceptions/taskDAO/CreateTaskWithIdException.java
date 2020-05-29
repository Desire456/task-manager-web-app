package org.netcracker.students.dao.exceptions.taskDAO;

public class CreateTaskWithIdException extends Exception{
    public CreateTaskWithIdException(){
        super();
    }

    public CreateTaskWithIdException(String message){
        super(message);
    }

    public CreateTaskWithIdException(Throwable e){
        super(e);
    }
}

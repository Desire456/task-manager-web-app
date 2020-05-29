package org.netcracker.students.dao.exceptions.taskDAO;

public class TaskIdAlreadyExistException extends Exception{
    public TaskIdAlreadyExistException(){
        super();
    }

    public TaskIdAlreadyExistException(String message){
        super(message);
    }

    public TaskIdAlreadyExistException(Throwable e){
        super(e);
    }
}

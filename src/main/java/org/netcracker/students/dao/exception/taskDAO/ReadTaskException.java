package org.netcracker.students.dao.exception.taskDAO;

public class ReadTaskException extends Exception{

    public ReadTaskException(String message){
        super(message);
    }

    public ReadTaskException(){
        super();
    }

    public ReadTaskException(Throwable e){
        super(e);
    }
}

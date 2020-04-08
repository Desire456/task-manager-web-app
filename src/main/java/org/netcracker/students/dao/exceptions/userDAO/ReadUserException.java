package org.netcracker.students.dao.exceptions.userDAO;

public class ReadUserException extends Exception {
    public ReadUserException(){
        super();
    }

    public ReadUserException(String message){
        super(message);
    }

    public ReadUserException(Throwable e){
        super(e);
    }
}

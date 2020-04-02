package org.netcracker.students.dao.exception.userDAO;

public class UpdateUserException extends Exception {
    public UpdateUserException(){
        super();
    }

    public UpdateUserException(String message){
        super(message);
    }

    public UpdateUserException(Throwable e){
        super(e);
    }
}

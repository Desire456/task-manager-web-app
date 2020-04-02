package org.netcracker.students.dao.exception.userDAO;

public class DeleteUserException extends Exception {
    public DeleteUserException(){
        super();
    }

    public DeleteUserException(String message){
        super(message);
    }

    public DeleteUserException(Throwable e){
        super(e);
    }
}

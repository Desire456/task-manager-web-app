package org.netcracker.students.dao.exceptions.userDAO;

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

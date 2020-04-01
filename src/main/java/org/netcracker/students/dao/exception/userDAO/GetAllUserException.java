package org.netcracker.students.dao.exception.userDAO;

public class GetAllUserException extends Exception {
    public GetAllUserException(){
        super();
    }

    public GetAllUserException(String message){
        super(message);
    }

    public GetAllUserException(Throwable e){
        super(e);
    }
}

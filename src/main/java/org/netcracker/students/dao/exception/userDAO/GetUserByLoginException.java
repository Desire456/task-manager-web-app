package org.netcracker.students.dao.exception.userDAO;

public class GetUserByLoginException extends Exception {
    public GetUserByLoginException(){
        super();
    }

    public GetUserByLoginException(String message){
        super(message);
    }

    public GetUserByLoginException(Throwable e){
        super(e);
    }
}

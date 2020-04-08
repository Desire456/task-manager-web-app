package org.netcracker.students.dao.exceptions.userDAO;

public class CreateUserException extends Exception {
    public CreateUserException(){
        super();
    }

    public CreateUserException(String message){
        super(message);
    }

    public CreateUserException(Throwable e){
        super(e);
    }
}

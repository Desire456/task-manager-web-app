package org.netcracker.students.controller.utils.hashing.exceptions;

public class ValidatePasswordException extends Exception {
    public ValidatePasswordException(){
        super();
    }

    public ValidatePasswordException(String message){
        super(message);
    }

    public ValidatePasswordException(Throwable e){
        super(e);
    }
}

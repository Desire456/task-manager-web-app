package org.netcracker.students.controller.utils.hashing.exceptions;

public class GeneratePasswordException extends Exception {
    public GeneratePasswordException(){
        super();
    }

    public GeneratePasswordException(String message){
        super(message);
    }

    public GeneratePasswordException(Throwable e){
        super(e);
    }
}

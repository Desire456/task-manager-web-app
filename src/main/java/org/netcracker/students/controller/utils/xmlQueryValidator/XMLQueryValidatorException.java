package org.netcracker.students.controller.utils.xmlQueryValidator;

public class XMLQueryValidatorException extends Exception {
    public XMLQueryValidatorException(){
        super();
    }

    public XMLQueryValidatorException(String message){
        super(message);
    }

    public XMLQueryValidatorException(Throwable e){
        super(e);
    }
}

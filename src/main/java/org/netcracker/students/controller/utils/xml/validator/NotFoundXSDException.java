package org.netcracker.students.controller.utils.xml.validator;

public class NotFoundXSDException extends Exception {
    public NotFoundXSDException(){
        super();
    }

    public NotFoundXSDException(String message){
        super(message);
    }

    public NotFoundXSDException(Throwable e){
        super(e);
    }
}

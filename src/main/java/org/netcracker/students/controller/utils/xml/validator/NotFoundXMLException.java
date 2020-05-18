package org.netcracker.students.controller.utils.xml.validator;

public class NotFoundXMLException extends Exception {
    public NotFoundXMLException(){
        super();
    }

    public NotFoundXMLException(String message){
        super(message);
    }

    public NotFoundXMLException(Throwable e){
        super(e);
    }
}

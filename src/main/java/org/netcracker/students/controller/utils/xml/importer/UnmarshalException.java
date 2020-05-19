package org.netcracker.students.controller.utils.xml.importer;

public class UnmarshalException extends Exception {

    public UnmarshalException(){
        super();
    }

    public UnmarshalException(String message){
        super(message);
    }

    public UnmarshalException(Throwable e){
        super(e);
    }
}

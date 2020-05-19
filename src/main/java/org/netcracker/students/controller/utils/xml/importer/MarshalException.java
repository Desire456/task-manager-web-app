package org.netcracker.students.controller.utils.xml.importer;

public class MarshalException extends Exception {

    public MarshalException(){
        super();
    }

    public MarshalException(String message){
        super(message);
    }

    public MarshalException(Throwable e){
        super(e);
    }
}

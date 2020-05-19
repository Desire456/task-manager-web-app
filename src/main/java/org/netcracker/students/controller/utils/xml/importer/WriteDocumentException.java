package org.netcracker.students.controller.utils.xml.importer;

public class WriteDocumentException extends Exception{

    public WriteDocumentException(){
        super();
    }

    public WriteDocumentException(String message){
        super(message);
    }

    public WriteDocumentException(Throwable e){
        super(e);
    }
}

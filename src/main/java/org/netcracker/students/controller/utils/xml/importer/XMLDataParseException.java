package org.netcracker.students.controller.utils.xml.importer;

public class XMLDataParseException extends Exception {
    public XMLDataParseException(){
        super();
    }

    public XMLDataParseException(String message){
        super(message);
    }

    public XMLDataParseException(Throwable e){
        super(e);
    }
}

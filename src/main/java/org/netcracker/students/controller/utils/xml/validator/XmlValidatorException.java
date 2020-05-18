package org.netcracker.students.controller.utils.xml.validator;

public class XmlValidatorException extends Exception {
    public XmlValidatorException(){
        super();
    }

    public XmlValidatorException(String message){
        super(message);
    }

    public XmlValidatorException(Throwable e){
        super(e);
    }
}

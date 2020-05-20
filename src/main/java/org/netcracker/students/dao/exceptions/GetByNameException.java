package org.netcracker.students.dao.exceptions;

public class GetByNameException extends Exception {
    public GetByNameException(){
        super();
    }

    public GetByNameException(String message){
        super(message);
    }

    public GetByNameException(Throwable e){
        super(e);
    }
}

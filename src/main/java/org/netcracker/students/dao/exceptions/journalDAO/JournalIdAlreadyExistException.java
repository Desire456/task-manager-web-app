package org.netcracker.students.dao.exceptions.journalDAO;

public class JournalIdAlreadyExistException extends Exception {
    public JournalIdAlreadyExistException(){
        super();
    }

    public JournalIdAlreadyExistException(String message){
        super(message);
    }

    public JournalIdAlreadyExistException(Throwable e){
        super(e);
    }
}

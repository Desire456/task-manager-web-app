package org.netcracker.students.dao.exception.journalDAO;

public class ReadJournalException extends Exception {
    public ReadJournalException(){
        super();
    }

    public ReadJournalException(String message){
        super(message);
    }

    public ReadJournalException(Throwable e){
        super(e);
    }
}

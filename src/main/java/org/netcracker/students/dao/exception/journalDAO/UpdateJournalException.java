package org.netcracker.students.dao.exception.journalDAO;

public class UpdateJournalException extends Exception {
    public UpdateJournalException(){
        super();
    }

    public UpdateJournalException(String message){
        super(message);
    }

    public UpdateJournalException(Throwable e){
        super(e);
    }
}

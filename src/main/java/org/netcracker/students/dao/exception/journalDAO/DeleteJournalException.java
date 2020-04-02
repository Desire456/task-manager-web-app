package org.netcracker.students.dao.exception.journalDAO;

public class DeleteJournalException extends Exception {
    public DeleteJournalException(){
        super();
    }

    public DeleteJournalException(String message){
        super(message);
    }

    public DeleteJournalException(Throwable e){
        super(e);
    }
}

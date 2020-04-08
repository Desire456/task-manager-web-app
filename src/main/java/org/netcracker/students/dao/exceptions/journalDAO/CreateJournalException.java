package org.netcracker.students.dao.exceptions.journalDAO;

public class CreateJournalException extends Exception {
    public CreateJournalException(String message){
        super(message);
    }

    public CreateJournalException(){
        super();
    }

    public CreateJournalException(Throwable e){
        super(e);
    }
}

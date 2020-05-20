package org.netcracker.students.dao.exceptions.journalDAO;

public class CreateJournalByIdException extends Exception {
    public CreateJournalByIdException(){
        super();
    }

    public CreateJournalByIdException(String message){
        super(message);
    }

    public CreateJournalByIdException(Throwable e){
        super(e);
    }
}

package org.netcracker.students.dao.exception.journalDAO;

public class GetAllJournalByUserIdException extends Exception {
    public GetAllJournalByUserIdException(){
        super();
    }

    public GetAllJournalByUserIdException(String message){
        super(message);
    }

    public GetAllJournalByUserIdException(Throwable e){
        super(e);
    }
}

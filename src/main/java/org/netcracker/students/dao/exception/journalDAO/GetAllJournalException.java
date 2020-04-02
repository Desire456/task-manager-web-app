package org.netcracker.students.dao.exception.journalDAO;

public class GetAllJournalException extends Exception {
    public GetAllJournalException(){
        super();
    }

    public GetAllJournalException(String message) {
        super(message);
    }

    public GetAllJournalException(Throwable e){
        super(e);
    }
}

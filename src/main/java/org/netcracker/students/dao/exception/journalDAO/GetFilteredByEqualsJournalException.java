package org.netcracker.students.dao.exception.journalDAO;

public class GetFilteredByEqualsJournalException extends Exception {
    public GetFilteredByEqualsJournalException(){
        super();
    }

    public GetFilteredByEqualsJournalException(String message){
        super(message);
    }

    public GetFilteredByEqualsJournalException(Throwable e){
        super(e);
    }
}

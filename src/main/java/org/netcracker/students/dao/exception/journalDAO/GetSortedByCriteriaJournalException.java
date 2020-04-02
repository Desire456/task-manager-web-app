package org.netcracker.students.dao.exception.journalDAO;

public class GetSortedByCriteriaJournalException extends Exception {
    public GetSortedByCriteriaJournalException(){
        super();
    }

    public GetSortedByCriteriaJournalException(String message){
        super(message);
    }

    public GetSortedByCriteriaJournalException(Throwable e){
        super(e);
    }
}

package org.netcracker.students.dao.exception.taskDAO;

public class GetSortedByCriteriaTaskException extends Exception {
    public GetSortedByCriteriaTaskException(String message){
        super(message);
    }

    public GetSortedByCriteriaTaskException(){
        super();
    }

    public GetSortedByCriteriaTaskException(Throwable e){
        super(e);
    }
}

package org.netcracker.students.dao.exception.taskDAO;

public class GetFilteredByEqualsTaskException extends Exception {
    public GetFilteredByEqualsTaskException(String message){
        super(message);
    }

    public GetFilteredByEqualsTaskException(){
        super();
    }

    public GetFilteredByEqualsTaskException(Throwable e){
        super(e);
    }
}

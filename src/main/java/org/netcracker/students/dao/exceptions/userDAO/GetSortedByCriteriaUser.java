package org.netcracker.students.dao.exceptions.userDAO;

public class GetSortedByCriteriaUser extends Exception {
    public GetSortedByCriteriaUser() {
        super();
    }

    public GetSortedByCriteriaUser(String message) {
        super(message);
    }

    public GetSortedByCriteriaUser(Throwable e) {
        super(e);
    }
}

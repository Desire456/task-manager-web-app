package org.netcracker.students.dao.interfaces;


import org.netcracker.students.model.Task;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface TasksDAO {
    public Task create(String name, String status, String description, Date plannedDate, Date dateOfDone, Integer journalId) throws SQLException;

    public Task read(int id) throws SQLException;

    public void update(Task task) throws SQLException;

    public void delete(int id) throws SQLException;

    public List<Task> getAll() throws SQLException;

    public List<Task> getAll(int journalId) throws SQLException;

    public List<Task> getSortedByCriteria(int journalId, String column, String criteria) throws SQLException;

    public List<Task> getFilteredByPattern(int journalId, String column, String pattern, String criteria) throws SQLException;

    public List<Task> getFilteredByEquals(int journalId, String column, String equal, String criteria) throws SQLException;
}

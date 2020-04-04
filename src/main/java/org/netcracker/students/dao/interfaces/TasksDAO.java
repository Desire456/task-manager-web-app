package org.netcracker.students.dao.interfaces;


import org.netcracker.students.dao.exception.taskDAO.*;
import org.netcracker.students.model.Task;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface TasksDAO {
    public Task create(String name, String status, String description,
                       Timestamp plannedDate, Timestamp dateOfDone, Integer journalId) throws CreateTaskException;

    public Task read(int id) throws ReadTaskException;

    public void update(Task task) throws UpdateTaskException;

    public void delete(int id) throws DeleteTaskException;

    public List<Task> getAll() throws GetAllTaskException;

    public List<Task> getAll(int journalId) throws GetAllTaskException;

    public List<Task> getSortedByCriteria(int journalId, String column,
                                          String criteria) throws GetSortedByCriteriaTaskException;

    public List<Task> getFilteredByPattern(int journalId, String column, String pattern,
                                           String criteria) throws GetFilteredByPatternTaskException;

    public List<Task> getFilteredByEquals(int journalId, String column, String equal,
                                          String criteria) throws GetFilteredByEqualsTaskException;
}

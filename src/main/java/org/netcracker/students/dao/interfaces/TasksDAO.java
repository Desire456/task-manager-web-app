package org.netcracker.students.dao.interfaces;


import org.netcracker.students.dao.exception.taskDAO.*;
import org.netcracker.students.model.Task;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface TasksDAO {
    public Task create(String name, String status, String description, Date plannedDate, Date dateOfDone, Integer journalId) throws SQLException, CreateTaskException;

    public Task read(int id) throws SQLException, ReadTaskException;

    public void update(Task task) throws SQLException, UpdateTaskException;

    public void delete(int id) throws SQLException, DeleteTaskException;

    public List<Task> getAll() throws SQLException, GetAllTaskException;

    public List<Task> getAll(int journalId) throws SQLException, GetAllTaskException;

    public List<Task> getSortedByCriteria(int journalId, String column, String criteria) throws SQLException, GetSortedByCriteriaTaskException;

    public List<Task> getFilteredByPattern(int journalId, String column, String pattern, String criteria) throws SQLException, GetFilteredByPatternTaskException;

    public List<Task> getFilteredByEquals(int journalId, String column, String equal, String criteria) throws SQLException, GetFilteredByEqualsTaskException;
}

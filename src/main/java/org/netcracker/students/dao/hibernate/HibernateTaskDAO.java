package org.netcracker.students.dao.hibernate;

import org.netcracker.students.dao.exceptions.taskDAO.*;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.model.Task;
import org.netcracker.students.model.dto.TaskDTO;

import java.sql.Timestamp;
import java.util.List;

public class HibernateTaskDAO implements TasksDAO {
    @Override
    public Task create(String name, String status, String description, Timestamp plannedDate, Timestamp dateOfDone, Integer journalId) throws CreateTaskException {
        return null;
    }

    @Override
    public Task read(int id) throws ReadTaskException {
        return null;
    }

    @Override
    public void update(Task task) throws UpdateTaskException {

    }

    @Override
    public void deleteByTaskId(int id) throws DeleteTaskException {

    }

    @Override
    public List<TaskDTO> getAll() throws GetAllTaskException {
        return null;
    }

    @Override
    public List<TaskDTO> getAll(int journalId) throws GetAllTaskException {
        return null;
    }

    @Override
    public List<TaskDTO> getSortedByCriteria(int journalId, String column, String criteria) throws GetSortedByCriteriaTaskException {
        return null;
    }

    @Override
    public List<TaskDTO> getFilteredByPattern(int journalId, String column, String pattern, String criteria) throws GetFilteredByPatternTaskException {
        return null;
    }

    @Override
    public List<TaskDTO> getFilteredByEquals(int journalId, String column, String equal, String criteria) throws GetFilteredByEqualsTaskException {
        return null;
    }
}

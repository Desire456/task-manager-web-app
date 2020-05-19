package org.netcracker.students.dao.interfaces;


import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.taskDAO.*;
import org.netcracker.students.model.dto.TaskDTO;
import org.netcracker.students.model.Task;

import java.sql.Timestamp;
import java.util.List;

public interface TasksDAO {
    Task create(String name, String status, String description, Timestamp plannedDate,
                Timestamp dateOfDone, Integer journalId) throws CreateTaskException, NameAlreadyExistException;

    Task create(int id, String name, String status, String description, Timestamp plannedDate,
                Timestamp dateOfDone, Integer journalId) throws CreateTaskException, NameAlreadyExistException;

    Task read(int id) throws ReadTaskException;

    void update(Task task) throws UpdateTaskException;

    void deleteByTaskId(int id) throws DeleteTaskException;

    List<Task> getAllByJournalId(int journalId) throws GetAllTaskException;

    List<TaskDTO> getAll() throws GetAllTaskException;

    List<TaskDTO> getAll(int journalId) throws GetAllTaskException;

    List<Task> getAllByNameAndJournalId(String name, int journalId) throws GetAllTaskException;

    List<TaskDTO> getSortedByCriteria(int journalId, String column,
                                      String criteria) throws GetSortedByCriteriaTaskException;

    List<TaskDTO> getFilteredByPattern(int journalId, String column, String pattern,
                                       String criteria) throws GetFilteredByPatternTaskException;

    List<TaskDTO> getFilteredByEquals(int journalId, String column, String equal,
                                      String criteria) throws GetFilteredByEqualsTaskException;
}

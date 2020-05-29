package org.netcracker.students.controller;

import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.*;
import org.netcracker.students.dao.hibernate.HibernateManagerDAO;
import org.netcracker.students.dao.interfaces.ManagerDAO;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;
import org.netcracker.students.dao.postrgresql.PostgreSQLManagerDAO;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.model.dto.TaskDTO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to control the tasks hiding the DAO layer
 * @see Task
 */
public class TaskController {
    private TasksDAO tasksDAO;

    private static TaskController instance;

    public static synchronized TaskController getInstance() throws GetConnectionException {
        if (instance == null) {
            instance = new TaskController();
        }
        return instance;
    }

    private TaskController() throws GetConnectionException {
        ManagerDAO managerDAO = HibernateManagerDAO.getInstance();
        try {
            this.tasksDAO = managerDAO.getTasksDao();
        } catch (SQLException e) {
            throw new GetConnectionException(DAOErrorConstants.GET_CONNECTION_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    /**
     * Get task by id
     * @param taskId
     * @return
     * @throws ReadTaskException can't get task by this id or problems with connection to DB
     */
    public Task getTask(int taskId) throws ReadTaskException {
        return tasksDAO.read(taskId);
    }

    /**
     * Add task with id if journal with the same name in database doesn't exist
     * @param task
     * @throws CreateTaskException problems with connection to DB
     * @throws NameAlreadyExistException task with the same name already exist
     */
    public void addTask(Task task) throws CreateTaskException, NameAlreadyExistException {
        try {
            if (tasksDAO.getByName(task.getName(), task.getJournalId()) != null)
                throw new NameAlreadyExistException(String.format(DAOErrorConstants.NAME_ALREADY_EXIST_TASK_EXCEPTION_MESSAGE,
                        task.getName()));
        } catch (GetTaskByNameException e) {
            throw new CreateTaskException(DAOErrorConstants.CREATE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
        tasksDAO.create(task.getName(), task.getStatus(), task.getDescription(), Timestamp.valueOf(task.getPlannedDate()),
                null, task.getJournalId());
    }

    /**
     * Add task with id if task with the same name in database doesn't exist or with the same id doesn't
     * exist
     * @param task
     * @throws CreateTaskException problems with connection to DB
     * @throws NameAlreadyExistException task with the same name already exist
     * @throws TaskIdAlreadyExistException task with the same id already exist
     */
    public void addTaskWithId(Task task) throws CreateTaskException, NameAlreadyExistException, TaskIdAlreadyExistException {
        try {
            if (tasksDAO.getByName(task.getName(), task.getJournalId()) != null)
                throw new NameAlreadyExistException(String.format(DAOErrorConstants.NAME_ALREADY_EXIST_TASK_EXCEPTION_MESSAGE,
                        task.getName()));
            if (tasksDAO.read(task.getId()) != null)
                throw new TaskIdAlreadyExistException(DAOErrorConstants.TASK_ID_ALREADY_EXIST_EXCEPTION_MESSAGE + task.getId());
        } catch (GetTaskByNameException | ReadTaskException e) {
            throw new CreateTaskException(DAOErrorConstants.CREATE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
        Timestamp dateOfDoneStamp = task.getDateOfDone() == null ? null : Timestamp.valueOf(task.getDateOfDone());
        tasksDAO.create(task.getId(), task.getName(), task.getStatus(), task.getDescription(), Timestamp.valueOf(task.getPlannedDate()),
                dateOfDoneStamp, task.getJournalId());
    }

    /**
     * Delete task by id
     * @param taskId
     * @throws DeleteTaskException can't delete task by this id or problems with connection to DB
     */
    public void deleteTask(int taskId) throws DeleteTaskException {
        tasksDAO.deleteByTaskId(taskId);
    }

    /**
     * Update old task to new task with check change planned date
     * @param newTask
     * @throws UpdateTaskException can't update to new task or problems with connection to DB
     */
    public void editTask(Task newTask) throws UpdateTaskException {
        Task oldTask = null;
        try {
            oldTask = this.getTask(newTask.getId());
        } catch (ReadTaskException ignored) {
        }
        if (oldTask != null) {
            newTask.setStatus(newTask.getPlannedDate().compareTo(oldTask.getPlannedDate()) == 0 ?
                    ControllerConstants.STATUS_PLANNED : ControllerConstants.STATUS_DEFERRED);
            tasksDAO.update(newTask);
        }
    }

    /**
     * Assign COMPLETED or OVERDUE status (OVERDUE - when planned date is before then current date)
     * @param tasks
     * @throws UpdateTaskException
     */
    public void finishTasks(ArrayList<Task> tasks) throws UpdateTaskException {
        for (Task task : tasks) {
            task.setDateOfDone(LocalDateTime.now());
            if (LocalDateTime.now().isBefore(task.getPlannedDate())) {
                task.setStatus(ControllerConstants.STATUS_COMPLETED);
            } else {
                task.setStatus(ControllerConstants.STATUS_OVERDUE);
            }
            tasksDAO.update(task);
        }
    }

    /**
     * Get tasks by ids
     * @param ids ids of tasks
     * @return
     * @throws ReadTaskException can't get task by this ids or problems with connection to DB
     */
    public ArrayList<Task> getTasks(String ids) throws ReadTaskException {
        int id;
        String[] mas = ids.split(" ");
        ArrayList<Task> tasks = new ArrayList<>();
        for (String str : mas) {
            id = Integer.parseInt(str);
            tasks.add(this.getTask(id));
        }
        return tasks;
    }

    /**
     * Get all tasks by id of journal
     * @param journalId
     * @return
     * @throws GetAllTaskException can't get tasks by this id of journal or problems with connection to DB
     */
    public List<TaskDTO> getAll(int journalId) throws GetAllTaskException {
        return tasksDAO.getAll(journalId);
    }

    /**
     * Get tasks by ids
     * @param ids ids of tasks
     * @return
     * @throws ReadTaskException can't get task by this ids or problems with connection to DB
     */
    public List<Task> getTasks(List<Integer> ids) throws ReadTaskException {
        List<Task> tasks = new ArrayList<>();
        for (Integer id : ids) tasks.add(this.getTask(id));
        return tasks;
    }

    /**
     * Get all tasks by journal ids
     * @param ids list of journal ids
     * @return
     * @throws GetAllTaskException can't get all tasks by this journal ids or problems with connection to DB
     */
    public List<Task> getTasksByJournalIds(List<Integer> ids) throws GetAllTaskException {
        List<Task> tasks = new ArrayList<>();
        for (Integer journalId : ids) tasks.addAll(tasks.size(), this.tasksDAO.getAllByJournalId(journalId));
        return tasks;
    }

    /**
     * Filter tasks by LIKE pattern or only equal
     * @param journalId id of journal which stores this tasks
     * @param column filter by this column
     * @param pattern desired match
     * @param criteria ascending or descending
     * @param equal LIKE if false, EQUAL if true
     * @return list of DTO tasks
     * @throws GetFilteredByEqualsTaskException can't filter by equal tasks or problems with connection to DB
     * @throws GetFilteredByPatternTaskException can't filter by like tasks or problems with connection to DB
     */
    public List<TaskDTO> getFilteredTasks(int journalId, String column, String pattern, String criteria, boolean equal) throws GetFilteredByEqualsTaskException, GetFilteredByPatternTaskException {
        if (equal) {
            return tasksDAO.getFilteredByEquals(journalId, column, pattern, criteria);
        } else {
            String likePattern = ControllerConstants.LIKE_PATTERN_CONSTANT
                    + pattern + ControllerConstants.LIKE_PATTERN_CONSTANT;
            return tasksDAO.getFilteredByPattern(journalId,
                    column, likePattern, criteria);
        }
    }

    /**
     * Delete tasks by ids
     * @param ids ids of tasks
     * @throws DeleteTaskException Can't delete tasks by this ids or problem with connection to DB
     */
    public void deleteTask(String ids) throws DeleteTaskException {
        int id;
        String[] mas = ids.split(" ");
        for (String str : mas) {
            id = Integer.parseInt(str);
            this.deleteTask(id);
        }
    }
}

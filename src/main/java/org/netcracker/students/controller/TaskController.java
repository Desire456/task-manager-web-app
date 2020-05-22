package org.netcracker.students.controller;

import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.CreateJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.GetJournalByNameException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.*;
import org.netcracker.students.dao.hibernate.HibernateManagerDAO;
import org.netcracker.students.dao.interfaces.ManagerDAO;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.model.dto.TaskDTO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is using for change journal of tasks, to control journal
 *
 * @see Journal
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
     * Getter function by id
     *
     * @return desired task
     */

    public Task getTask(int taskId) throws ReadTaskException {
        return tasksDAO.read(taskId);
    }

    /**
     * Add function
     *
     * @param task - new task
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
        tasksDAO.create(task.getId(), task.getName(), task.getStatus(), task.getDescription(), Timestamp.valueOf(task.getPlannedDate()),
                null, task.getJournalId());
    }

    /**
     * Delete function by id
     */

    public void deleteTask(int taskId) throws DeleteTaskException {
        tasksDAO.deleteByTaskId(taskId);
    }

    /**
     * Change function by id
     *
     * @param newTask - new task
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

    public List<TaskDTO> getAll(int journalId) throws GetAllTaskException {
        return tasksDAO.getAll(journalId);
    }

    public List<Task> getTasks(List<Integer> ids) throws ReadTaskException {
        List<Task> tasks = new ArrayList<>();
        for (Integer id : ids) tasks.add(this.getTask(id));
        return tasks;
    }

    public List<Task> getTasksByJournalIds(List<Integer> ids) throws GetAllTaskException {
        List<Task> tasks = new ArrayList<>();
        for (Integer journalId : ids) tasks.addAll(tasks.size(), this.tasksDAO.getAllByJournalId(journalId));
        return tasks;
    }

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

    public void deleteTask(String ids) throws DeleteTaskException {
        int id;
        String[] mas = ids.split(" ");
        for (String str : mas) {
            id = Integer.parseInt(str);
            this.deleteTask(id);
        }
    }
}

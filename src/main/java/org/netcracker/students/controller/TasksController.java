package org.netcracker.students.controller;

import org.netcracker.students.dao.exceptions.taskDAO.*;
import org.netcracker.students.dao.interfaces.DAOManager;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.postrgresql.PostgreSQLDAOManager;
import org.netcracker.students.dto.TaskDTO;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;

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

public class TasksController {
    private TasksDAO tasksDAO;

    private static TasksController instance;

    public static synchronized TasksController getInstance() {
        if (instance == null) {
            instance = new TasksController();
        }
        return instance;
    }

    private TasksController() {
        DAOManager daoManager = PostgreSQLDAOManager.getInstance();
        try {
            this.tasksDAO = daoManager.getTasksDao();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void addTask(Task task) throws CreateTaskException {
        tasksDAO.create(task.getName(), task.getStatus(), task.getDescription(), Timestamp.valueOf(task.getPlannedDate()),
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

    public void changeTask(Task newTask) throws UpdateTaskException {
        Task oldTask = null;
        try {
            oldTask = this.getTask(newTask.getId());
        } catch(ReadTaskException ignored) {
        }
        if(oldTask != null) {
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

    public List<TaskDTO> getFilteredTasks(int userId, String column, String pattern, String criteria, boolean equal) throws GetFilteredByEqualsTaskException, GetFilteredByPatternTaskException {
        if (equal) {
            return tasksDAO.getFilteredByEquals(userId, column, pattern, criteria);
        } else {
            String likePattern = ControllerConstants.LIKE_PATTERN_CONSTANT
                    + pattern + ControllerConstants.LIKE_PATTERN_CONSTANT;
            return tasksDAO.getFilteredByPattern(userId,
                    column, likePattern, criteria);
        }
    }

    public void deleteTasks(String ids) throws DeleteTaskException {
        int id;
        String[] mas = ids.split(" ");
        for (String str : mas) {
            id = Integer.parseInt(str);
            this.deleteTask(id);
        }
    }
}

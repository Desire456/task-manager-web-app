package org.netcracker.students.controller;

import org.netcracker.students.dao.exception.taskDAO.*;
import org.netcracker.students.dao.interfaces.DAOManager;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.postrgresql.PostgreSQLDAOManager;
import org.netcracker.students.dto.TaskDTO;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;

import java.sql.SQLException;
import java.sql.Timestamp;
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


    /*void addJournal(int id, Journal journal) {
        tasksDAO.
        this.journals.put(id, journal);
    }

    void deleteJournal(int id) {
        this.journals.remove(id);
    }*/

    /**
     * Function deleted all notifications for real journal is tasks
     */
   /* public Journal getJournal(int id) {
        return journals.get(id);
    }*/

    /**
     * Getter function by id
     *
     * @return desired task
     */

    public Task getTask(int journalId, int taskId) throws ReadTaskException {
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
        tasksDAO.update(newTask);
    }

    /**
     * Function for cancelling task by id
     */

    /*public void cancelTask(int journalId, int taskId) {
        this.journals.get(journalId).getTask(taskId).setStatus("CANCELED");
    }*/


    /**
     * @return unmodifiable list of all tasks
     */

    public List<TaskDTO> getAll(int journalId) throws GetAllTaskException {
        return tasksDAO.getAll(journalId);
    }

    /*public void restoreTasks(Journal journal) {
        List<Task> tasks = journal.getAll();
        for (Task task : tasks) {
            if (task.getDateOfDone() == null) {
                if (task.getPlannedDate().isBefore(LocalDateTime.now())) {
                    task.setStatus("OVERDUE");
                }
            }
            this.getJournal(journal.getId()).addTask(task);
        }
    }*/

    /*public void cancelTask(int journalId, ArrayList<Integer> ids) {
        for (int i : ids) {
            this.cancelTask(journalId, i);
        }
    }*/

   /* public void deleteTask(int journalId, ArrayList<Integer> ids) {
        for (int i : ids) {
            this.deleteTask(journalId, i);
        }
    }*/

    public void deleteTask(String ids) throws DeleteTaskException {
        int id;
        String[] mas = ids.split(" ");
        for (String str : mas) {
            id = Integer.parseInt(str);
            this.deleteTask(id);
        }
    }
}

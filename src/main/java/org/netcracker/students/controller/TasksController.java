package org.netcracker.students.controller;

import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Class is using for change journal of tasks, to control journal
 *
 * @see Journal
 */

public class TasksController {
    private HashMap<Integer, Journal> journals;

    private static TasksController instance;

    public static synchronized TasksController getInstance() {
        if (instance == null) {
            instance = new TasksController();
        }
        return instance;
    }

    private TasksController() {
        this.journals = new HashMap<>();
    }


    void addJournal(int id, Journal journal) {
        this.journals.put(id, journal);
    }

    void deleteJournal(int id) {
        this.journals.remove(id);
    }

    /**
     * Function deleted all notifications for real journal is tasks
     */
    public Journal getJournal(int id) {
        return journals.get(id);
    }

    /**
     * Getter function by id
     *
     * @return desired task
     */

    public Task getTask(int journalId, int taskId) {
        return this.journals.get(journalId).getTask(taskId);
    }

    /**
     * Add function
     *
     * @param task - new task
     */

    public void addTask(int journalId, Task task) {
        this.journals.get(journalId).addTask(task);
    }

    /**
     * Delete function by id
     */

    public void deleteTask(int journalId, int taskId) {
        this.journals.get(journalId).deleteTask(taskId);
    }

    /**
     * Change function by id
     *
     * @param newTask - new task
     */

    public void changeTask(int journalId, int taskId, Task newTask) {
        this.journals.get(journalId).changeTask(taskId, newTask);
    }

    /**
     * Function for cancelling task by id
     */

    public void cancelTask(int journalId, int taskId) {
        this.journals.get(journalId).getTask(taskId).setStatus("CANCELED");
    }


    /**
     * @return unmodifiable list of all tasks
     */

    public List<Task> getAll(int journalId) {
        return Collections.unmodifiableList(this.journals.get(journalId).getAll());
    }

    public void restoreTasks(Journal journal) {
        List<Task> tasks = journal.getAll();
        for (Task task : tasks) {
            if (task.getDateOfDone() == null) {
                if (task.getPlannedDate().isBefore(LocalDateTime.now())) {
                    task.setStatus("OVERDUE");
                }
            }
            this.getJournal(journal.getId()).addTask(task);
        }
    }

    public void cancelTask(int journalId, ArrayList<Integer> ids) {
        for (int i : ids) {
            this.cancelTask(journalId, i);
        }
    }

    public void deleteTask(int journalId, ArrayList<Integer> ids) {
        for (int i : ids) {
            this.deleteTask(journalId, i);
        }
    }

    public void deleteTask(int journalId, String ids) {
        int id = 0;
        for (int i = 0; i < ids.length() - 1; i += 2) {
            id = Character.getNumericValue(ids.charAt(i));
            this.deleteTask(journalId, id);
        }
    }


}

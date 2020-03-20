package org.netcracker.students.controller;

import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Status;
import org.netcracker.students.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class is using for change journal of tasks, to control journal
 *
 * @see Journal
 */

public class TasksController {
    private int id;
    private Journal journal;

    public TasksController(int id, Journal journal) {
        this.id = id;
        this.journal = journal;
    }


    public int getId() {
        return id;
    }

    /**
     * Function deleted all notifications for real journal is tasks
     */
    public Journal getJournal() {
        return journal;
    }

    /**
     * Getter function by id
     *
     * @return desired task
     */

    public Task getTask(int id) {
        return journal.getTask(id);
    }

    /**
     * Add function
     *
     * @param task - new task
     */

    public void addTask(Task task) {
        journal.addTask(task);
    }

    /**
     * Delete function by id
     */

    public void deleteTask(int id) {
        journal.deleteTask(id);
    }

    /**
     * Change function by id
     *
     * @param task2 - new task
     */

    public void changeTask(int id, Task task2) {
        journal.changeTask(id, task2);
    }

    /**
     * Function for cancelling task by id
     */

    public void cancelTask(int id) {
        journal.getTask(id).setStatus("CANCELED");
    }


    /**
     * @return unmodifiable list of all tasks
     */

    public List<Task> getAll() {
        return Collections.unmodifiableList(journal.getAll());
    }

    public void restoreTasks(Journal journal) {
        List<Task> tasks = journal.getAll();
        for (Task task : tasks) {
            if (task.getDateOfDone() == null) {
                if (task.getPlannedDate().isBefore(LocalDateTime.now())) {
                    task.setStatus("OVERDUE");
                }
            }
            this.addTask(task);
        }

    }

    public void cancelTask(ArrayList<Integer> ids) {
        for (int i : ids) {
            this.cancelTask(i);
        }
    }

    public void deleteTask(ArrayList<Integer> ids) {
        for (int i : ids) {
            this.deleteTask(i);
        }
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }


    public boolean isTaskInJournal(Task task) {
        return journal.isTaskInJournal(task);
    }
}

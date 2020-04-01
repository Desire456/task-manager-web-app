package org.netcracker.students.factories;


import org.netcracker.students.model.Task;

import java.time.LocalDate;

/**
 * Factory pattern class creating tasks
 *
 * @see Task
 */

public class TaskFactory {

    /**
     * Constructor with certain values
     *
     * @param id          - id of task
     * @param name        - name of task
     * @param description - description of task
     * @param plannedDate - planned date of task
     * @param status      - current status of task
     * @return new task with specified arguments
     */

    public Task createTask(int id, String name, String description, LocalDate plannedDate, String status) {
        return new Task(id, name, description, plannedDate, status);
    }

    public Task createTask(String name, String description, LocalDate plannedDate, String status, int journalId) {
        return new Task(name, description, plannedDate, status, journalId);
    }

    public Task createTask(int id, int journalId, String name, String description, LocalDate plannedDate, LocalDate dateOfDone, String status) {
        return new Task(id, journalId, name, description, plannedDate, dateOfDone, status);
    }

    /**
     * Copy constructor
     *
     * @param task - copy object
     * @return copy of argument
     */

    public Task createTask(Task task) {
        return new Task(task);
    }
}
